package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface IServicioRepository extends JpaRepository<Servicio, Integer> {

    // Buscar servicios por nombre, sin distinguir mayúsculas/minúsculas
    List<Servicio> findByNombreServicioContainingIgnoreCase(String nombre_servicio);

    // Obtener todos los servicios paginados
    Page<Servicio> findAll(Pageable pageable);
}
