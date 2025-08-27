package org.esfe.BeatySaly.servicios.implementaciones;

import org.esfe.BeatySaly.repositorios.AuthRepository;
import org.esfe.BeatySaly.servicios.interfaces.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService implements IAuthService {

    private final AuthRepository authRepository;

    @Autowired
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        String[] tables = {"administradores", "trabajadores", "clientes"};

        for (String tabla : tables) {
            String passwordHash = authRepository.getPasswordHash(correo, tabla);
            String role = authRepository.getRole(correo, tabla); // ← viene como "ADMIN", "TRABAJADOR", etc.

            if (passwordHash != null && role != null) {
                // Agregamos el prefijo "ROLE_" para que Spring lo reconozca
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());
                return new User(
                        correo,
                        passwordHash,
                        true, true, true, true,
                        Collections.singletonList(authority)
                );
            }
        }

        throw new UsernameNotFoundException("Usuario no encontrado: " + correo);
    }
}
