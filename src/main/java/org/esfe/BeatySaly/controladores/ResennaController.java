package org.esfe.BeatySaly.controladores;

import org.esfe.BeatySaly.modelos.Resenna;
import org.esfe.BeatySaly.servicios.interfaces.IResennaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resennas")
public class ResennaController {

    @Autowired
    private IResennaService resennaService;

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
    @PostMapping
    public Resenna crear(@RequestBody Resenna resenna) {
        return resennaService.crear(resenna);
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
