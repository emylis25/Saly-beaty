package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IServicioRepository extends JpaRepository<Servicio, Integer> {

    @Query("""
        SELECT s
        FROM Servicio s
        WHERE
            (:nombreServicio IS NULL OR LOWER(s.nombreServicio) LIKE LOWER(CONCAT('%', :nombreServicio, '%')))
        AND (:precioMin IS NULL OR s.precio >= :precioMin)
        AND (:precioMax IS NULL OR s.precio <= :precioMax)
    """)
    Page<Servicio> buscarServicios(
            @Param("nombreServicio") String nombreServicio,
            @Param("precioMin") Double precioMin,
            @Param("precioMax") Double precioMax,
            Pageable pageable
    );
}
