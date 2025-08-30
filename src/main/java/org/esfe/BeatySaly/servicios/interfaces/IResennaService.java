package org.esfe.BeatySaly.servicios.interfaces;
import org.esfe.BeatySaly.modelos.Cliente;
import org.esfe.BeatySaly.modelos.Resenna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IResennaService {

    List<Resenna> obtenerTodos();

    Page<Resenna> buscarTodosPaginados(Pageable pageable);

    Resenna buscarPorId(int id);

    Resenna crear(Resenna resenna);

    Resenna editar(Resenna resenna);

    void eliminarPorId(int id);

    Page<Resenna> buscarPorNombreTrabajadorYCliente(
            String nombreTrabajador,
            String nombreCliente,
            Pageable pageable
    );

    Resenna guardar(Resenna resenna);
    List<Resenna> obtenerPorCliente(Cliente cliente);

}
