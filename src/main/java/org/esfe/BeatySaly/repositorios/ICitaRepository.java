package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.Cita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
    @Repository
    public interface ICitaRepository extends JpaRepository<Cita, Integer> {

        @Query("""
        SELECT c
        FROM Cita c
        WHERE
            (:mes IS NULL OR MONTH(c.fechaHora) = :mes)
        AND (:anio IS NULL OR YEAR(c.fechaHora) = :anio)
        AND (:servicioId IS NULL OR c.servicio.id = :servicioId)
        AND (:nombreTrabajador IS NULL OR LOWER(c.trabajador.nombre) LIKE LOWER(CONCAT('%', :nombreTrabajador, '%')))
        AND (:nombreCliente IS NULL OR LOWER(c.cliente.nombre) LIKE LOWER(CONCAT('%', :nombreCliente, '%')))
    """)
        Page<Cita> buscarCitas(
                @Param("mes") Integer mes,
                @Param("anio") Integer anio,
                @Param("servicioId") Integer servicioId,
                @Param("nombreTrabajador") String nombreTrabajador,
                @Param("nombreCliente") String nombreCliente,
                Pageable pageable
        );
    }
