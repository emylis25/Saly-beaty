package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.Trabajador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITrabajadorRepository extends JpaRepository<Trabajador, Integer> {

    Page<Trabajador> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
