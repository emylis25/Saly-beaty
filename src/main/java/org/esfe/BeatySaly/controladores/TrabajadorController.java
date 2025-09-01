package org.esfe.BeatySaly.controladores;

import org.esfe.BeatySaly.modelos.Trabajador;
import org.esfe.BeatySaly.servicios.interfaces.ITrabajadorService;
import org.esfe.BeatySaly.servicios.utilerias.PdfClienteGeneratorService;
import org.esfe.BeatySaly.servicios.utilerias.PdfTrabajadorGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/trabajadores")
public class TrabajadorController {

    @Autowired
    private ITrabajadorService trabajadorService;
    @Autowired
    private PdfTrabajadorGeneratorService pdfGeneratorService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Trabajador> trabajadores = trabajadorService.buscarTodosPaginados(pageable);
        model.addAttribute("trabajadores", trabajadores);

        int totalPages = trabajadores.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "trabajador/index";
    }

    @GetMapping("/create")
    public String create(Trabajador trabajador){
        return "trabajador/create";
    }

    @PostMapping("/save")
    public String save(Trabajador trabajador, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("error", "No se pudo guardar el trabajador debido a un error.");
            return "trabajador/create"; // Vuelve al formulario si hay errores
        }

        if (trabajador.getId() == 0) {
            trabajadorService.crear(trabajador);
            attributes.addFlashAttribute("msg", "Trabajador creado correctamente.");
        } else {
            trabajadorService.actualizar(trabajador);
            attributes.addFlashAttribute("msg", "Trabajador editado correctamente.");
        }

        return "redirect:/trabajadores"; // ⬅️ Redirección única y consistente
    }

    @GetMapping("/details")
    public String details(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correo = authentication.getName(); // Suponiendo que el correo es el username

        Trabajador trabajador = trabajadorService.buscarPorCorreo(correo);
        if (trabajador == null) {
            return "redirect:/trabajadores";
        }

        model.addAttribute("trabajador", trabajador);
        return "trabajador/details"; // Vista: src/main/resources/templates/trabajador/details.html
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") int id, Model model) {
        Trabajador trabajador = trabajadorService.obtenerPorId(id);
        if (trabajador == null) {
            return "redirect:/trabajadores";
        }

        model.addAttribute("trabajador", trabajador);
        return "trabajador/details"; // Asegúrate que esta vista exista
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        Trabajador trabajador = trabajadorService.obtenerPorId(id);
        if (trabajador == null) {
            return "redirect:/trabajadores";
        }
        model.addAttribute("trabajador", trabajador);
        return "trabajador/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Trabajador trabajador) {
        trabajadorService.actualizar(trabajador);
        return "redirect:/trabajadores/details/" + trabajador.getId();
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") int id, Model model){
        Trabajador trabajador = trabajadorService.obtenerPorId(id);
        if (trabajador == null) {
            return "redirect:/trabajadores";
        }
        model.addAttribute("trabajador", trabajador);
        return "trabajador/remove";
    }

    @PostMapping("/remove")
    public String deleteTrabajador(@RequestParam("id") int id, RedirectAttributes attributes) {
        try {
            // Llama al método de tu servicio para eliminar
            trabajadorService.remove(id);
            attributes.addFlashAttribute("msg", "Trabajador eliminado correctamente.");
        } catch (Exception e) {
            attributes.addFlashAttribute("errorMsg", "Hubo un error al eliminar el trabajador.");
        }
        return "redirect:/trabajadores"; // Redirige a la lista principal
    }

    @GetMapping("/reportegeneral/{visualizacion}")
    public ResponseEntity<byte[]> reporteGeneralTrabajadores(@PathVariable("visualizacion") String visualizacion) {

        try {
            List<Trabajador> trabajadores = trabajadorService.obtenerTodos();

            // Genera el PDF usando la plantilla HTML de trabajadores
            byte[] pdfBytes = pdfGeneratorService.generatePdfFromHtml("reportes/rpTrabajadores", "trabajadores", trabajadores);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition", visualizacion + "; filename=reporte_trabajadores.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
