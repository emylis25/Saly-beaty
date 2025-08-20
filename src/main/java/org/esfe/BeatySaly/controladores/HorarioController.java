package org.esfe.BeatySaly.controladores;

import org.esfe.BeatySaly.modelos.Horario;
import org.esfe.BeatySaly.servicios.interfaces.IHorarioService;
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
@RequestMapping("/horarios")
public class HorarioController {
    @Autowired
    private IHorarioService horarioService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Horario> horarios = horarioService.buscarTodosPaginados(pageable);
        model.addAttribute("horarios", horarios);

        int totalPages = horarios.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "horario/index";
    }

    @GetMapping("/create")
    public String create(Horario horario){
        return "horario/create";
    }

    @PostMapping("/save")
    public String save(Horario horario, BindingResult result, Model model, RedirectAttributes attributes){
        if(result.hasErrors()){
            model.addAttribute("horario", horario);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "horario/create";
        }

        horarioService.actualizar(horario);
        attributes.addFlashAttribute("msg", "Horario creado o editado correctamente");
        return "redirect:/horarios";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") int id, Model model){
        Horario horario = horarioService.obtenerPorId(id);
        if (horario == null) {
            return "redirect:/horarios";
        }
        model.addAttribute("horario", horario);
        return "horario/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        Horario horario = horarioService.obtenerPorId(id);
        if (horario == null) {
            return "redirect:/horarios";
        }
        model.addAttribute("horario", horario);
        return "horario/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") int id, Model model){
        Horario horario = horarioService.obtenerPorId(id);
        if (horario == null) {
            return "redirect:/horarios";
        }
        model.addAttribute("horario", horario);
        return "horario/delete";
    }

    @PostMapping("/delete")
    public String delete(Horario horario, RedirectAttributes attributes){
        horarioService.eliminar(horario.getId());
        attributes.addFlashAttribute("msg", "Horario eliminado correctamente");
        return "redirect:/horarios";
    }
}