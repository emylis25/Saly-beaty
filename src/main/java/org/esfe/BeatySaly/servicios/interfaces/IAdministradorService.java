package org.esfe.BeatySaly.servicios.interfaces;

import org.esfe.BeatySaly.modelos.Administrador;
import java.util.List;

public interface IAdministradorService {

    // Obtener todos los administradores
    List<Administrador> obtenerTodos();

    // Obtener un administrador por ID
    Administrador obtenerPorId(Long id);

    // Crear un nuevo administrador
    Administrador crear(Administrador administrador);

    // Actualizar un administrador existente
    Administrador actualizar(Administrador administrador);

    // Eliminar un administrador por ID
    void eliminar(Long id);

    // Buscar administrador por username
    Administrador buscarPorUsername(String username);
}

