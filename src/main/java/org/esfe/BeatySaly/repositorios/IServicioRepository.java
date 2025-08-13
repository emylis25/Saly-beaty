package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServicioRepository extends JpaRepository<Servicio, Integer> {

    Page<Servicio> findByNombreServicioContainingIgnoreCase(String nombreServicio, Pageable pageable);
}
