package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.Trabajador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ITrabajadorRepository extends JpaRepository<Trabajador, Integer> {

    List<Trabajador> findByNombreContainingIgnoreCase(String nombre);

    Page<Trabajador> findAll(Pageable pageable);

}
