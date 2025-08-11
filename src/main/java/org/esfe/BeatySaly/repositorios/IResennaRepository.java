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
public interface IResennaRepository extends JpaRepository<Cita, Integer> {

    // Buscar reseñas por nombre del trabajdor
    @Query("SELECT r FROM Resenna r WHERE LOWER(r.trabajador.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Page<Resenna> findByTrabajadorNombreContainingIgnoreCase(@Param("nombre") String nombre, Pageable pageable);

    //  busca por calificaciones
    List<Resenna> findByCalificacionGreaterThanEqual(int calificacionMinima);

}
