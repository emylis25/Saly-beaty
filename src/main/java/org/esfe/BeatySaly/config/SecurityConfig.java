package org.esfe.BeatySaly.config;

import org.esfe.BeatySaly.servicios.implementaciones.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthService authService;

    public SecurityConfig(AuthService authService) {
        this.authService = authService;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return authService;
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // endpoints públicos
                        .requestMatchers("/home", "/login", "/css/**", "/js/**", "/imagenes/**", "/clientes/save", "/clientes/create").permitAll()
                        // admin
                        .requestMatchers("/vistaAdministrador", "/listarCitas", "/reportes").hasRole("ADMIN")
                        // trabajador
                        .requestMatchers("/vistaTrabajador", "/trabajador").hasRole("TRABAJADOR")
                        // cliente
                        .requestMatchers("/vistaCliente", "/citas/nueva", "/resennas/crear").hasRole("CLIENTE")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("correo")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> {
                            String role = authentication.getAuthorities().iterator().next().getAuthority();
                            switch (role) {
                                case "ROLE_ADMIN" -> response.sendRedirect("/vistaAdministrador");
                                case "ROLE_TRABAJADOR" -> response.sendRedirect("/vistaTrabajador");
                                case "ROLE_CLIENTE" -> response.sendRedirect("/vistaCliente");
                                default -> response.sendRedirect("/home");
                            }
                        })
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );
        return http.build();
    }

}