package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.Cita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICitaRepository extends JpaRepository<Cita, Integer> {

    // Búsqueda por nombre del trabajador y nombre del cliente
    Page<Cita> findByTrabajador_NombreContainingIgnoreCaseAndCliente_NombreContainingIgnoreCase(
            String nombreTrabajador,
            String nombreCliente,
            Pageable pageable
    );
}
