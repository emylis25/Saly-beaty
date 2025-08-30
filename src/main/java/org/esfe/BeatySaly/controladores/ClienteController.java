package org.esfe.BeatySaly.controladores;

import org.esfe.BeatySaly.modelos.Cliente;
import org.esfe.BeatySaly.servicios.interfaces.IClienteService;
import org.esfe.BeatySaly.servicios.utilerias.PdfClienteGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private IClienteService clienteService;
    @Autowired
    private PdfClienteGeneratorService pdfGeneratorService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Cliente> clientes = clienteService.buscarTodosPaginados(pageable);
        model.addAttribute("clientes", clientes);

        int totalPages = clientes.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "cliente/index";
    }

    @GetMapping("/create")
    public String create(Cliente cliente){
        return "cliente/create";
    }

    @PostMapping("/save")
    public String save(Cliente cliente, BindingResult result, Model model, RedirectAttributes attributes){
        if(result.hasErrors()){
            model.addAttribute("cliente", cliente);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "cliente/create";
        }

        // 🔒 Encriptar contraseña
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode(cliente.getPassword());
        cliente.setPassword(passwordEncriptada);

        // 👤 Asignar rol fijo
        cliente.setRol("CLIENTE");

        clienteService.guardar(cliente);
        attributes.addFlashAttribute("msg", "Cliente creado correctamente");
        return "redirect:/login";
    }


    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") int id, Model model){
        Cliente cliente = clienteService.obtenerPorId(id);
        if (cliente == null) {
            // Manejar caso donde el cliente no se encuentra
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", cliente);
        return "cliente/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        Cliente cliente = clienteService.obtenerPorId(id);
        if (cliente == null) {
            // Manejar caso donde el cliente no se encuentra
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", cliente);
        return "cliente/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") int id, Model model){
        Cliente cliente = clienteService.obtenerPorId(id);
        if (cliente == null) {
            // Manejar caso donde el cliente no se encuentra
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", cliente);
        return "cliente/delete";
    }

    @PostMapping("/delete")
    public String delete(Cliente cliente, RedirectAttributes attributes){
        clienteService.eliminar(cliente.getId());
        attributes.addFlashAttribute("msg", "Cliente eliminado correctamente");
        return "redirect:/clientes";
    }

    @GetMapping("/reportegeneral/{visualizacion}")
    public ResponseEntity<byte[]> ReporteGeneral(@PathVariable("visualizacion") String visualizacion) {

        try {
            List<Cliente> cliente = clienteService.obtenerTodos();

            // Genera el PDF. Si hay un error aquí, la excepción será capturada.
            byte[] pdfBytes = pdfGeneratorService.generatePdfFromHtml("reportes/rpClientes", "clientes", cliente);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            // inline= vista previa, attachment=descarga el archivo
            headers.add("Content-Disposition", visualizacion+"; filename=reporte_general.pdf");
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
