package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.Trabajador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITrabajadorRepository extends JpaRepository<Trabajador, Integer> {

    Trabajador findByCorreo(String correo);

    Page<Trabajador> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
