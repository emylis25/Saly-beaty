package org.esfe.BeatySaly.controladores;

import org.esfe.BeatySaly.modelos.Cita;
import org.esfe.BeatySaly.servicios.interfaces.ICitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private ICitaService citaService;

    // Obtener todas las citas
    @GetMapping
    public List<Cita> obtenerTodos() {
        return citaService.obtenerTodos();
    }

    // Obtener citas paginadas
    @GetMapping("/paginadas")
    public Page<Cita> obtenerTodosPaginados(Pageable pageable) {
        return citaService.buscarTodosPaginados(pageable);
    }

    // Obtener cita por ID
    @GetMapping("/{id}")
    public Cita obtenerPorId(@PathVariable int id) {
        return citaService.buscarPorId(id);
    }

    // Crear una nueva cita
    @PostMapping
    public Cita crear(@RequestBody Cita cita) {
        return citaService.crear(cita);
    }

    // Actualizar cita existente
    @PutMapping("/{id}")
    public Cita actualizar(@PathVariable int id, @RequestBody Cita cita) {
        cita.setId(id);
        return citaService.editar(cita);
    }

    // Eliminar cita por ID
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        citaService.eliminarPorId(id);
    }

    // Buscar citas por nombre de trabajador y cliente
    @GetMapping("/buscar")
    public Page<Cita> buscarPorTrabajadorYCliente(
            @RequestParam(required = false) String trabajador,
            @RequestParam(required = false) String cliente,
            Pageable pageable) {
        return citaService.buscarPorNombreTrabajadorYCliente(trabajador, cliente, pageable);
    }
}
