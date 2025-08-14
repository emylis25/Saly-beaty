package org.esfe.BeatySaly.repositorios;

import org.esfe.BeatySaly.modelos.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdministradorRepository extends JpaRepository<Administrador, Long> {
    Administrador findByUsername(String username);
}
