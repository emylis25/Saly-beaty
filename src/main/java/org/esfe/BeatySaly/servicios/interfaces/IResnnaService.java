package org.esfe.BeatySaly.servicios.interfaces;
import org.esfe.BeatySaly.modelos.Resenna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IResnnaService {

    List<Resenna> obtenerTodos();

    Page<Resenna> buscarTodosPaginados(Pageable pageable);

    Resenna buscarPorId(Integer id);

    Resenna crear(Resenna resenna);

    Resenna editar(Resenna resenna);

    void eliminarPorId(Integer id);

    Page<Resenna> buscarPorNombreTrabajadorYCliente(
            String nombreTrabajador,
            String nombreCliente,
            Pageable pageable
    );
}
