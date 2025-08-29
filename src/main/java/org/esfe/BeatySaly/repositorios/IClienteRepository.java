package org.esfe.BeatySaly.repositorios;
import org.esfe.BeatySaly.modelos.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Integer> {

    // Método existente para búsqueda paginada
    Page<Cliente> findByNombreContainingIgnoreCaseAndCorreoContainingIgnoreCase(
            String nombre,
            String correo,
            Pageable pageable
    );

    // ✅ Nuevo método para buscar por correo directamente
    Optional<Cliente> findByCorreo(String correo);
}
