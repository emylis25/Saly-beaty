package org.esfe.BeatySaly.controladores;


import org.esfe.BeatySaly.modelos.Cliente;
import org.esfe.BeatySaly.modelos.Trabajador;
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

    @GetMapping("/vistaCliente")
    public String vistaCliente() {
        // Retorna el nombre del template Thymeleaf
        return "cliente/vistaCliente";
    }

    @GetMapping("/vistaTrabajador")
    public String vistaTrabajador() {
        // Retorna el nombre del template Thymeleaf
        return "trabajador/vistaTrabajador";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "home/login"; // Nombre del archivo HTML en src/main/resources/templates
    }
}