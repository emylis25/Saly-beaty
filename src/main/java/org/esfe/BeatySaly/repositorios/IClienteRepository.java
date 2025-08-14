package org.esfe.BeatySaly.repositorios;
import org.esfe.BeatySaly.modelos.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Integer> {

    Page<Cliente> findByNombreContainingIgnoreCaseAndCorreoContainingIgnoreCase(
            String nombre,
            String correo,
            Pageable pageable
    );
}
