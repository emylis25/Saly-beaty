package org.esfe.BeatySaly.servicios.interfaces;

import org.esfe.BeatySaly.modelos.Horario;
import java.util.List;

public interface IHorarioService {

    // Obtener todos los horarios
    List<Horario> obtenerTodos();

    // Obtener un horario por ID
    Horario obtenerPorId(Integer id);

    // Crear un nuevo horario
    Horario crear(Horario horario);

    // Actualizar un horario existente
    Horario actualizar(Horario horario);

    // Eliminar un horario por ID
    void eliminar(Integer id);
}