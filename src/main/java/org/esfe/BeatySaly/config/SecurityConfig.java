package org.esfe.BeatySaly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Inyecta el bean DataSource proporcionado por Spring Boot
    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public UserDetailsManager users() {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

        // Consulta SQL para encontrar el usuario en cualquiera de las tres tablas
        users.setUsersByUsernameQuery(
                "SELECT correo, password, true as enabled FROM administradores WHERE correo = ? " +
                        "UNION ALL " +
                        "SELECT correo, password, true as enabled FROM trabajadores WHERE correo = ? " +
                        "UNION ALL " +
                        "SELECT correo, password, true as enabled FROM clientes WHERE correo = ?"
        );

        // Consulta SQL para obtener el rol (autoridad) del usuario
        users.setAuthoritiesByUsernameQuery(
                "SELECT correo, 'ROLE_ADMIN' as authority FROM administradores WHERE correo = ? " +
                        "UNION ALL " +
                        "SELECT correo, 'ROLE_TRABAJADOR' as authority FROM trabajadores WHERE correo = ? " +
                        "UNION ALL " +
                        "SELECT correo, 'ROLE_CLIENTE' as authority FROM clientes WHERE correo = ?"
        );

        return users;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        // ✅ Permite el acceso a páginas públicas, CSS y JS
                        .requestMatchers("/home", "/login", "/vistaAdministrador", "/css/", "/js/").permitAll()

                        // ✅ Los roles ahora coinciden con la base de datos y la lógica
                        .requestMatchers("/administrador/").hasRole("ADMIN")
                        .requestMatchers("/trabajador/").hasRole("TRABAJADOR")
                        .requestMatchers("/cliente/").hasRole("CLIENTE")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/home", true)
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
