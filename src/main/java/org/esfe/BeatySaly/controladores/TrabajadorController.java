package org.esfe.BeatySaly.controladores;

import org.esfe.BeatySaly.modelos.Trabajador;
import org.esfe.BeatySaly.servicios.interfaces.ITrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/trabajadores")
public class TrabajadorController {

    @Autowired
    private ITrabajadorService trabajadorService;

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
            return "trabajador/create";
        }

        if (trabajador.getId() == 0) {
            trabajadorService.crear(trabajador);
            attributes.addFlashAttribute("msg", "Trabajador creado correctamente.");
        } else {
            trabajadorService.actualizar(trabajador);
            attributes.addFlashAttribute("msg", "Trabajador editado correctamente.");
        }

        return "redirect:/trabajadores";
    }

    @GetMapping("/details")
    public String details(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correo = authentication.getName(); // Esto devuelve el username (correo si lo configuraste así)

        Trabajador trabajador = trabajadorService.buscarPorCorreo(correo);
        if (trabajador == null) {
            return "redirect:/trabajadores";
        }

        model.addAttribute("trabajador", trabajador);
        return "trabajador/details";
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

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") int id, Model model){
        Trabajador trabajador = trabajadorService.obtenerPorId(id);
        if (trabajador == null) {
            return "redirect:/trabajadores";
        }
        model.addAttribute("trabajador", trabajador);
        return "trabajador/delete";
    }

    @PostMapping("/delete")
    public String delete(Trabajador trabajador, RedirectAttributes attributes){
        trabajadorService.eliminar(trabajador.getId());
        attributes.addFlashAttribute("msg", "Trabajador eliminado correctamente.");
        return "redirect:/trabajadores";
    }
}
