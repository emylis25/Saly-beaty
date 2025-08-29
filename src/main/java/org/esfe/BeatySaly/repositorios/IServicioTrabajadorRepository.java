package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.ServicioTrabajador;
import org.esfe.BeatySaly.modelos.Trabajador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IServicioTrabajadorRepository extends JpaRepository<ServicioTrabajador, Integer> {

    // Filtro paginado por nombre de trabajador y servicio
    Page<ServicioTrabajador> findByTrabajadorNombreContainingIgnoreCaseAndServicioNombreServicioContainingIgnoreCase(
            String nombreTrabajador,
            String nombreServicio,
            Pageable pageable
    );

    // Para obtener las asignaciones por servicio
    List<ServicioTrabajador> findByServicioId(Integer idServicio);
}
