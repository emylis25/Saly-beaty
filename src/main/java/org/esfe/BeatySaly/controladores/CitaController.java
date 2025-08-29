package org.esfe.BeatySaly.controladores;

import org.esfe.BeatySaly.modelos.Cita;
import org.esfe.BeatySaly.modelos.Cliente;
import org.esfe.BeatySaly.modelos.Servicio;
import org.esfe.BeatySaly.modelos.Trabajador;
import org.esfe.BeatySaly.servicios.interfaces.ICitaService;
import org.esfe.BeatySaly.servicios.interfaces.IClienteService;
import org.esfe.BeatySaly.servicios.interfaces.IServicioService;
import org.esfe.BeatySaly.servicios.interfaces.ITrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


@Controller
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private ICitaService citaService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private ITrabajadorService trabajadorService;

    @Autowired
    private IServicioService servicioService;



    @GetMapping("/listarCitas")
    @PreAuthorize("hasRole('ADMIN')")
    public String listarCitas(Model model, Pageable pageable) {
        Page<Cita> citas = citaService.buscarTodosPaginados(pageable);
        model.addAttribute("citas", citas);
        return "citas/listarCitas"; // plantilla Thymeleaf
    }


    @GetMapping("/buscar")
    public String buscarPorTrabajadorYCliente(@RequestParam(required = false) String trabajador,
                                              @RequestParam(required = false) String cliente,
                                              Pageable pageable,
                                              Model model) {
        Page<Cita> citas = citaService.buscarPorNombreTrabajadorYCliente(trabajador, cliente, pageable);
        model.addAttribute("citas", citas);
        model.addAttribute("trabajador", trabajador);
        model.addAttribute("cliente", cliente);
        return "citas/listarCitas";
    }
    @GetMapping("/nueva")
    public String mostrarFormularioCita(Model model) {
        Cita cita = new Cita();
        cita.setServicio(new Servicio());     // ← evita null
        cita.setTrabajador(new Trabajador()); // ← evita null
        model.addAttribute("cita", cita);
        model.addAttribute("formAction", "/citas");

        // 🔐 Obtener el correo del usuario autenticado
        String correo = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            correo = userDetails.getUsername(); // normalmente es el correo
        }

        // 🔍 Buscar el cliente por correo
        Cliente cliente = clienteService.obtenerPorCorreo(correo);
        model.addAttribute("cliente", cliente);

        model.addAttribute("trabajadores", trabajadorService.obtenerTodos());
        model.addAttribute("servicios", servicioService.obtenerTodos());

        return "citas/formularioCita";
    }



    @GetMapping("/editar/{id}")
    public String editarCita(@PathVariable int id, Model model) {
        Cita cita = citaService.buscarPorId(id);
        model.addAttribute("cita", cita);
        model.addAttribute("formAction", "/citas/actualizar/" + cita.getId());

        model.addAttribute("clientes", clienteService.obtenerTodos());
        model.addAttribute("trabajadores", trabajadorService.obtenerTodos());
        model.addAttribute("servicios", servicioService.obtenerTodos());

        return "citas/formularioCita";
    }

    @PostMapping
    public String guardarCita(@ModelAttribute Cita cita, RedirectAttributes redirectAttrs, Model model) {
        // Obtener el correo del cliente autenticado
        String correo = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            correo = userDetails.getUsername();
        }

        // Buscar el cliente por correo y asignarlo
        Cliente cliente = clienteService.obtenerPorCorreo(correo);
        cita.setCliente(cliente);

        // Reconstruir trabajador y servicio
        Trabajador trabajador = trabajadorService.obtenerPorId(cita.getTrabajador().getId());
        Servicio servicio = servicioService.buscarPorId(cita.getServicio().getId());
        cita.setTrabajador(trabajador);
        cita.setServicio(servicio);

        // Guardar la cita
        Cita citaGuardada = citaService.crear(cita);

        // Pasar la cita a la vista de confirmación
        model.addAttribute("cita", citaGuardada);

        return "citas/confirmacion"; // nueva vista para el cliente
    }


    @PostMapping("/actualizar/{id}")
    public String actualizarCita(@PathVariable int id, @ModelAttribute Cita cita, RedirectAttributes redirectAttrs) {
        cita.setId(id);
        citaService.editar(cita);
        redirectAttrs.addFlashAttribute("exito", "Cita actualizada correctamente.");
        return "redirect:/citas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCita(@PathVariable int id, RedirectAttributes redirectAttrs) {
        citaService.eliminarPorId(id);
        redirectAttrs.addFlashAttribute("exito", "Cita eliminada correctamente.");
        return "redirect:/citas";
    }
}