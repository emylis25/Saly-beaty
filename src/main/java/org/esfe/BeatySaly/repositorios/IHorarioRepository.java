package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface IHorarioRepository extends JpaRepository<Horario, Integer> {

    // Buscar horarios por el nombre del trabajador.
    @Query("SELECT h FROM Horario h WHERE LOWER(h.trabajador.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Horario> findByTrabajadorNombreContainingIgnoreCase(@Param("nombre") String nombre);

    // Buscar horarios por el día de la semana.
    @Query("SELECT h FROM Horario h WHERE LOWER(h.diaSemana) = LOWER(:diaSemana)")
    List<Horario> findByDiaSemanaIgnoreCase(@Param("diaSemana") String diaSemana);

    // Buscar horarios para un trabajador específico por su ID.
    List<Horario> findByTrabajadorId(int idTrabajador);

    // Buscar horarios que se superponen con un rango de tiempo dado para un día y trabajador específico.
    @Query("SELECT h FROM Horario h WHERE h.trabajador.id = :trabajadorId AND h.diaSemana = :diaSemana AND " +
            "(:horaInicio < h.horaFin AND :horaFin > h.horaInicio)")
    List<Horario> findHorariosSolapados(
            @Param("trabajadorId") int trabajadorId,
            @Param("diaSemana") String diaSemana,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin);
}
