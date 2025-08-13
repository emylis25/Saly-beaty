package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.Horario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHorarioRepository extends JpaRepository<Horario, Integer> {

    // Método único con filtros opcionales por trabajador y día de la semana
    Page<Horario> findByTrabajadorNombreContainingIgnoreCaseAndDiaSemanaContainingIgnoreCase(
            String nombreTrabajador,
            String diaSemana,
            Pageable pageable
    );
}
