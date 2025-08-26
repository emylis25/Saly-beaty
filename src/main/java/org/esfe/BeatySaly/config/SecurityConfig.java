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
    @Bean
    public UserDetailsManager customUsers(DataSource dataSource){
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery("select login, clave, status from usuarios where login = ?");
        users.setAuthoritiesByUsernameQuery("select u.login, r.nombre from usuario_rol ur " +
                "inner join usuarios u on u.id = ur.usuario_id " +
                "inner join roles r on r.id = ur.rol_id " +
                "where u.login = ?");

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
