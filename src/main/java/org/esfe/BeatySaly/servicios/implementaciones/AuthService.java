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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String[] tables = {"administradores", "trabajadores", "clientes"};

        for (String role : tables) {
            String passwordHash = authRepository.getPasswordHash(email, role);
            if (passwordHash != null) {
                // Se encontró el usuario. Crea el objeto UserDetails con su rol.
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());
                return new User(email, passwordHash, Collections.singletonList(authority));
            }
        }

        throw new UsernameNotFoundException("Usuario no encontrado con el email: " + email);
    }
}
