package org.esfe.BeatySaly.servicios.interfaces;

import org.esfe.BeatySaly.modelos.Horario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IHorarioService {

    // Obtener todos los horarios
    List<Horario> obtenerTodos();

    Page<Horario> buscarTodosPaginados(Pageable pageable);

    // Obtener un horario por ID
    Horario obtenerPorId(int id);


    // Crear un nuevo horario
    Horario crear(Horario horario);

    // Actualizar un horario existente
    Horario actualizar(Horario horario);

    // Eliminar un horario por ID
    void eliminar(int id);

    Page<Horario> buscarPorTrabajadorYDia(String nombreTrabajador, String diaSemana, Pageable pageable);
}