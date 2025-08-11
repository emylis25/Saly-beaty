package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
    @Repository
    public interface ICitaRepository extends JpaRepository<Cita, Integer> {

        // Buscar citas por mes, y servicio
        @Query("SELECT c FROM Cita c WHERE MONTH(c.fechaHora) = :mes AND YEAR(c.fechaHora) = :anio AND c.servicio.id = :servicioId")
        List<Cita> findByMesAndServicio(@Param("mes") int mes,
                                        @Param("anio") int anio,
                                        @Param("servicioId") int servicioId);

        // Buscar por nombre del trabajador
        @Query("SELECT c FROM Cita c WHERE LOWER(c.trabajador.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
        List<Cita> findByTrabajadorNombreContainingIgnoreCase(@Param("nombre") String nombre);

        //Por nombre de Cliente.
        @Query("SELECT c FROM Cita c WHERE LOWER(c.cliente.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
        List<Cita> findByClienteNombreContainingIgnoreCase(@Param("nombre") String nombre);
    }
