package org.esfe.BeatySaly.controladores;
import org.esfe.BeatySaly.modelos.Servicio;
import org.esfe.BeatySaly.servicios.interfaces.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    @Autowired
    private IServicioService servicioService;

    // Obtener todos los servicios
    @GetMapping
    public List<Servicio> obtenerTodos() {
        return servicioService.obtenerTodos();
    }

    // Obtener servicios paginados
    @GetMapping("/paginados")
    public Page<Servicio> obtenerTodosPaginados(Pageable pageable) {
        return servicioService.buscarTodosPaginados(pageable);
    }

    // Obtener servicio por ID
    @GetMapping("/{id}")
    public Servicio obtenerPorId(@PathVariable int id) {
        return servicioService.buscarPorId(id);
    }

    // Crear nuevo servicio
    @PostMapping
    public Servicio crear(@RequestBody Servicio servicio) {
        return servicioService.crear(servicio);
    }

    // Actualizar servicio existente
    @PutMapping("/{id}")
    public Servicio actualizar(@PathVariable int id, @RequestBody Servicio servicio) {
        servicio.setId(id);
        return servicioService.editar(servicio);
    }

    // Eliminar servicio por ID
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        servicioService.eliminarPorId(id);
    }

    // Buscar servicios por nombre
    @GetMapping("/buscar")
    public Page<Servicio> buscarPorNombre(
            @RequestParam String nombreServicio,
            Pageable pageable) {
        return servicioService.buscarPorNombreServicio(nombreServicio, pageable);
    }
}
