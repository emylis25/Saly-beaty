package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServicioRepository extends JpaRepository<Servicio, Integer> {

    Page<Servicio> findByNombreServicioContainingIgnoreCase(String nombreServicio, Pageable pageable);
}
