package org.esfe.BeatySaly.servicios.interfaces;

import org.esfe.BeatySaly.modelos.Cita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICitaService {

        List<Cita> obtenerTodos();

        Page<Cita> buscarTodosPaginados(Pageable pageable);

        Cita buscarPorId(int id);

        Cita crear(Cita cita);

        Cita editar(Cita cita);

        void eliminarPorId(int id);

        Page<Cita> buscarPorNombreTrabajadorYCliente(
                String nombreTrabajador,
                String nombreCliente,
                Pageable pageable
        );
    }
