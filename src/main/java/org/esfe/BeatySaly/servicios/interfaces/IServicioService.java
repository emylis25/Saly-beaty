package org.esfe.BeatySaly.servicios.interfaces;

import org.esfe.BeatySaly.modelos.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IServicioService {

    List<Servicio> obtenerTodos();

    Page<Servicio> buscarTodosPaginados(Pageable pageable);

    Servicio buscarPorId(Integer id);

    Servicio crear(Servicio servicio);

    Servicio editar(Servicio servicio);

    void eliminarPorId(Integer id);

    Page<Servicio> buscarPorNombreServicio(
            String nombreServicio,
            Pageable pageable
    );
}
