package org.esfe.BeatySaly.servicios.interfaces;

import org.esfe.BeatySaly.modelos.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {

    // Obtener todos los clientes
    List<Cliente> obtenerTodos();

    Page<Cliente> buscarTodosPaginados(Pageable pageable);

    // Obtener un cliente por ID

    Cliente obtenerPorId(int id);
    // Obtener un cliente por correo
    Cliente obtenerPorCorreo(String correo);
    // Crear un nuevo cliente
//    Cliente crear(Cliente cliente);
//
//    // Actualizar un cliente existente
//    Cliente actualizar(Cliente cliente);
    Cliente guardar(Cliente cliente);

    // Eliminar un cliente por ID
    void eliminar(int id);

    Page<Cliente> buscarPorNombreYCorreo(String nombre, String correo, Pageable pageable);
}
