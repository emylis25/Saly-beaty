package org.esfe.BeatySaly.controladores;

import org.esfe.BeatySaly.modelos.Cliente;
import org.esfe.BeatySaly.modelos.Resenna;
import org.esfe.BeatySaly.modelos.Trabajador;
import org.esfe.BeatySaly.servicios.interfaces.IClienteService;
import org.esfe.BeatySaly.servicios.interfaces.IResennaService;
import org.esfe.BeatySaly.servicios.interfaces.ITrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
@RequestMapping("/resennas")
public class ResennaController {

    @Autowired
    private IResennaService resennaService;
    @Autowired
    private ITrabajadorService trabajadorService;

    @Autowired
    private IClienteService clienteService;

    // Obtener todas las reseñas
    @GetMapping
    public List<Resenna> obtenerTodos() {
        return resennaService.obtenerTodos();
    }

    // Obtener reseñas paginadas
    @GetMapping("/paginadas")
    public Page<Resenna> obtenerTodosPaginados(Pageable pageable) {
        return resennaService.buscarTodosPaginados(pageable);
    }

    // Obtener reseña por ID
    @GetMapping("/{id}")
    public Resenna obtenerPorId(@PathVariable int id) {
        return resennaService.buscarPorId(id);
    }

    // Crear nueva reseña
    @GetMapping("/crear")
    public String formCrearResenna(Model model,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        Resenna resenna = new Resenna();
        resenna.setTrabajador(new Trabajador());

        // Buscar cliente logueado en la DB
        Cliente clienteLogueado = clienteService.obtenerPorCorreo(userDetails.getUsername());
        if (clienteLogueado == null) {
            throw new RuntimeException("No se encontró el cliente logueado con correo: " + userDetails.getUsername());
        }
        resenna.setCliente(clienteLogueado); // Ahora sí, entidad con ID persistente

        model.addAttribute("resenna", resenna);

        // Pasar lista de trabajadores para el formulario
        List<Trabajador> trabajadores = trabajadorService.obtenerTodos();
        model.addAttribute("trabajadores", trabajadores);

        return "resennas/crear";
    }

    @PostMapping("/guardar")
    public String guardarResenna(@ModelAttribute("resenna") Resenna resenna,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                  RedirectAttributes redirectAttributes) {
        // Buscar cliente logueado en la BD
        Cliente clienteLogueado = clienteService.obtenerPorCorreo(userDetails.getUsername());
        if (clienteLogueado == null) {
            throw new RuntimeException("No se encontró el cliente logueado");
        }
        resenna.setCliente(clienteLogueado); // ✅ forzar cliente persistente

        redirectAttributes.addFlashAttribute("msg", "¡Reseña creada correctamente!");

        resennaService.crear(resenna); // usa crear(), no guardar() genérico si quieres validaciones
        return "redirect:/vistaCliente";
    }


    // Actualizar reseña existente
    @PutMapping("/{id}")
    public Resenna actualizar(@PathVariable int id, @RequestBody Resenna resenna) {
        resenna.setId(id);
        return resennaService.editar(resenna);
    }

    // Eliminar reseña por ID
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        resennaService.eliminarPorId(id);
    }

    // Buscar reseñas por trabajador y cliente
    @GetMapping("/buscar")
    public Page<Resenna> buscarPorTrabajadorYCliente(
            @RequestParam(required = false) String trabajador,
            @RequestParam(required = false) String cliente,
            Pageable pageable) {
        return resennaService.buscarPorNombreTrabajadorYCliente(trabajador, cliente, pageable);
    }
}
