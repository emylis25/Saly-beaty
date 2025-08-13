package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.ServicioTrabajador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServicioTrabajadorRepository extends JpaRepository<ServicioTrabajador, Integer> {

//     Método único con filtros opcionales por trabajador y servicio
    Page<ServicioTrabajador> findByTrabajadorNombreContainingIgnoreCaseAndServicioNombreServicioContainingIgnoreCase(
            String nombreTrabajador,
            String nombreServicio,
            Pageable pageable
    );
}
