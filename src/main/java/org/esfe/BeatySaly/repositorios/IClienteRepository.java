package org.esfe.BeatySaly.repositorios;


import org.esfe.BeatySaly.modelos.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query("""
        SELECT c FROM Cliente c
        WHERE (:nombre IS NULL OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
          AND (:correo IS NULL OR LOWER(c.correo) LIKE LOWER(CONCAT('%', :correo, '%')))
    """)
    Page<Cliente> buscarPorNombreYCorreo(
            @Param("nombre") String nombre,
            @Param("correo") String correo,
            Pageable pageable
    );
}
