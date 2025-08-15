package org.esfe.BeatySaly.servicios.interfaces;

import org.esfe.BeatySaly.modelos.Cliente;
import java.util.List;

public interface IClienteService {

    // Obtener todos los clientes
    List<Cliente> obtenerTodos();

    // Obtener un cliente por ID
    Cliente obtenerPorId(Integer id);

    // Crear un nuevo cliente
    Cliente crear(Cliente cliente);

    // Actualizar un cliente existente
    Cliente actualizar(Cliente cliente);

    // Eliminar un cliente por ID
    void eliminar(Integer id);
}
