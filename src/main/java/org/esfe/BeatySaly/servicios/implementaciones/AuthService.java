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
        // Enfoque simplificado: usa un solo método que busca en todas las tablas
        String[] tables = {"administradores", "trabajadores", "clientes"};

        for (String rol : tables) {
            String passwordHash = authRepository.getPasswordHash(correo, rol);
            if (passwordHash != null) {
                // Si el usuario es encontrado, Spring Security se encargará de la contraseña.
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + rol.toUpperCase());
                return new User(correo, passwordHash, Collections.singletonList(authority));
            }
        }

        // Si el bucle termina, el usuario no fue encontrado
        throw new UsernameNotFoundException("Usuario no encontrado: " + correo);
    }
}
