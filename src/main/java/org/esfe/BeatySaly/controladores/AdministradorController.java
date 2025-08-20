package org.esfe.BeatySaly.controladores;


import org.esfe.BeatySaly.modelos.Administrador;
import org.esfe.BeatySaly.servicios.interfaces.IAdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    @Autowired
    private IAdministradorService administradorService;

    // Obtener todos los administradores
    @GetMapping
    public ResponseEntity<List<Administrador>> obtenerTodos() {
        return ResponseEntity.ok(administradorService.obtenerTodos());
    }

    // Obtener administradores con paginación
    @GetMapping("/paginados")
    public ResponseEntity<Page<Administrador>> obtenerTodosPaginados(Pageable pageable) {
        return ResponseEntity.ok(administradorService.obtenerTodosPaginados(pageable));
    }

    // Obtener un administrador por ID
    @GetMapping("/{id}")
    public ResponseEntity<Administrador> obtenerPorId(@PathVariable int id) {
        Administrador administrador = administradorService.obtenerPorId(id);
        if (administrador != null) {
            return ResponseEntity.ok(administrador);
        }
        return ResponseEntity.notFound().build();
    }

    // Crear un nuevo administrador
    @PostMapping
    public ResponseEntity<Administrador> crear(@RequestBody Administrador administrador) {
        return ResponseEntity.ok(administradorService.crear(administrador));
    }

    // Actualizar un administrador existente
    @PutMapping("/{id}")
    public ResponseEntity<Administrador> actualizar(@PathVariable int id, @RequestBody Administrador administrador) {
        administrador.setId(id);
        Administrador actualizado = administradorService.actualizar(administrador);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar un administrador por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        administradorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar un administrador por correo
    @GetMapping("/buscar")
    public ResponseEntity<Administrador> buscarPorCorreo(@RequestParam String correo) {
        Administrador administrador = administradorService.buscarPorcorreo(correo);
        if (administrador != null) {
            return ResponseEntity.ok(administrador);
        }
        return ResponseEntity.notFound().build();
    }
}
