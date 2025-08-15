package org.esfe.BeatySaly.servicios.interfaces;

import org.esfe.BeatySaly.modelos.Trabajador;
import java.util.List;

public interface ITrabajadorService {

    // Obtener todos los trabajadores
    List<Trabajador> obtenerTodos();

    // Obtener un trabajador por ID
    Trabajador obtenerPorId(Integer id);

    // Crear un nuevo trabajador
    Trabajador crear(Trabajador trabajador);

    // Actualizar un trabajador existente
    Trabajador actualizar(Trabajador trabajador);

    // Eliminar un trabajador por ID
    void eliminar(Integer id);
}
