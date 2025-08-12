package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.Cita;
import org.esfe.BeatySaly.modelos.Resenna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;


@Repository
public interface IResennaRepository extends JpaRepository<Resenna, Integer> {

    @Query("""
        SELECT r
        FROM Resenna r
        WHERE
            (:nombreTrabajador IS NULL OR LOWER(r.trabajador.nombre) LIKE LOWER(CONCAT('%', :nombreTrabajador, '%')))
        AND (:nombreCliente IS NULL OR LOWER(r.cliente.nombre) LIKE LOWER(CONCAT('%', :nombreCliente, '%')))
        AND (:calificacion IS NULL OR r.calificacion = :calificacion)
    """)
    Page<Resenna> buscarResennas(
            @Param("nombreTrabajador") String nombreTrabajador,
            @Param("nombreCliente") String nombreCliente,
            @Param("calificacion") Integer calificacion,
            Pageable pageable
    );
}
