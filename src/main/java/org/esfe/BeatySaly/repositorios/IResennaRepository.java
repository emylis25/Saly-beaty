package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.Cita;
import org.esfe.BeatySaly.modelos.Resenna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IResennaRepository extends JpaRepository<Resenna, Integer> {

    Page<Resenna> findByTrabajadorNombreContainingIgnoreCaseAndClienteNombreContainingIgnoreCase(
            String nombreTrabajador,
            String nombreCliente,
            Pageable pageable
    );
}
