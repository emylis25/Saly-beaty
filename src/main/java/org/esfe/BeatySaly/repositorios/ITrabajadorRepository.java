package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.Trabajador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ITrabajadorRepository extends JpaRepository<Trabajador, Integer> {

    @Query("""
        SELECT t
        FROM Trabajador t
        WHERE (:nombre IS NULL OR LOWER(t.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
    """)
    Page<Trabajador> buscarPorNombre(
            @Param("nombre") String nombre,
            Pageable pageable
    );
}
