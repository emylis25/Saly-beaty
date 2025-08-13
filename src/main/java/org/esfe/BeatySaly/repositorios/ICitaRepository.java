package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.Cita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICitaRepository extends JpaRepository<Cita, Integer> {

    Page<Cita> findByTrabajadorNombreContainingIgnoreCaseAndClienteNombreContainingIgnoreCase(
            String nombreTrabajador,
            String nombreCliente,
            Pageable pageable
    );
}
