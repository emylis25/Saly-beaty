package org.esfe.BeatySaly.controladores;

import org.esfe.BeatySaly.modelos.ServicioTrabajador;
import org.esfe.BeatySaly.servicios.interfaces.IServicioTrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/servicio-trabajador")
public class ServicioTrabajadorController {

    @Autowired
    private IServicioTrabajadorService servicioTrabajadorService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ServicioTrabajador> servicioTrabajadores = servicioTrabajadorService.buscarTodosPaginados(pageable);
        model.addAttribute("servicioTrabajadores", servicioTrabajadores);

        int totalPages = servicioTrabajadores.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "servicio-trabajador/index";
    }

    @GetMapping("/create")
    public String create(ServicioTrabajador servicioTrabajador) {
        return "servicio-trabajador/create";
    }

    @PostMapping("/save")
    public String save(ServicioTrabajador servicioTrabajador,
                       BindingResult result,
                       RedirectAttributes attributes) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("error", "No se pudo guardar la asignación debido a un error.");
            return "servicio-trabajador/create";
        }

        // Determina si se crea o se actualiza
        if (servicioTrabajador.getId() == null) {
            servicioTrabajadorService.crear(servicioTrabajador);
            attributes.addFlashAttribute("msg", "Asignación creada correctamente.");
        } else {
            servicioTrabajadorService.actualizar(servicioTrabajador);
            attributes.addFlashAttribute("msg", "Asignación actualizada correctamente.");
        }

        return "redirect:/servicio-trabajador";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") int id, Model model) {
        ServicioTrabajador servicioTrabajador = servicioTrabajadorService.obtenerPorId(id);
        if (servicioTrabajador == null) {
            return "redirect:/servicio-trabajador";
        }
        model.addAttribute("servicioTrabajador", servicioTrabajador);
        return "servicio-trabajador/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        ServicioTrabajador servicioTrabajador = servicioTrabajadorService.obtenerPorId(id);
        if (servicioTrabajador == null) {
            return "redirect:/servicio-trabajador";
        }
        model.addAttribute("servicioTrabajador", servicioTrabajador);
        return "servicio-trabajador/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") int id, Model model) {
        ServicioTrabajador servicioTrabajador = servicioTrabajadorService.obtenerPorId(id);
        if (servicioTrabajador == null) {
            return "redirect:/servicio-trabajador";
        }
        model.addAttribute("servicioTrabajador", servicioTrabajador);
        return "servicio-trabajador/delete";
    }

    @PostMapping("/delete")
    public String delete(ServicioTrabajador servicioTrabajador, RedirectAttributes attributes) {
        servicioTrabajadorService.eliminar(servicioTrabajador.getId());
        attributes.addFlashAttribute("msg", "Asignación eliminada correctamente.");
        return "redirect:/servicio-trabajador";
    }
}
