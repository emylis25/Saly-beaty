package org.esfe.BeatySaly.controladores;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String index() {
        return "home/index";
    }

    @GetMapping("/vistaAdministrador")
    public String vistaAdministrador() {
        // Retorna el nombre del template Thymeleaf
        return "Administrador/vistaAdministrador";
    }
}
