package org.esfe.BeatySaly.servicios.interfaces;

import org.esfe.BeatySaly.modelos.ServicioTrabajador;
import org.esfe.BeatySaly.modelos.Trabajador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IServicioTrabajadorService {

    // Obtener todos los servicios de trabajadores
    List<ServicioTrabajador> obtenerTodos();

    // Obtener un servicio de trabajador por ID
    ServicioTrabajador obtenerPorId(int id);

    // Crear un nuevo servicio de trabajador
    ServicioTrabajador crear(ServicioTrabajador servicioTrabajador);

    // Actualizar un servicio de trabajador existente
    ServicioTrabajador actualizar(ServicioTrabajador servicioTrabajador);

    // Eliminar un servicio de trabajador por ID
    void eliminar(int id);

    // Obtener una lista paginada de servicios de trabajadores con filtros opcionales
    Page<ServicioTrabajador> buscarPorTrabajadorYServicio(String nombreTrabajador, String nombreServicio, Pageable pageable);

    Page<ServicioTrabajador> buscarTodosPaginados(Pageable pageable);

    List<Trabajador> obtenerTrabajadoresPorServicio(Integer idServicio);
}
