package org.esfe.BeatySaly.servicios.interfaces;

import org.esfe.BeatySaly.modelos.Trabajador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITrabajadorService {

    // Obtener todos los trabajadores
    List<Trabajador> obtenerTodos();

    Page<Trabajador> buscarTodosPaginados(Pageable pageable);

    // Obtener un trabajador por ID
    Trabajador obtenerPorId(int id);

    // Crear un nuevo trabajador
    Trabajador crear(Trabajador trabajador);

    // Actualizar un trabajador existente
    Trabajador actualizar(Trabajador trabajador);

    Trabajador buscarPorCorreo(String correo);

    // Eliminar un trabajador por ID


    void remove(int id);

    Page<Trabajador> buscarPorNombre(String nombre, Pageable pageable);
}
