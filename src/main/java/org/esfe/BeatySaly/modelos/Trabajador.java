package org.esfe.BeatySaly.modelos;

import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "trabajadores")
public class Trabajador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trabajador")
    private int id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo", unique = true, nullable = false, length = 100)
    private String correo;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    @Column(name = "red_social", length = 255)
    private String redSocial;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDateTime fechaIngreso;

    @Column(name = "foto_perfil", length = 255)
    private String fotoPerfil;

    @Column(name = "calificacion")
    private int calificacion;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "rol", nullable = false, length = 50)
    private String rol;

    // Relación Many-to-Many con Servicio (a través de ServicioTrabajador)
    // Esto se manejaría con @OneToMany en ServicioTrabajador, no directamente aquí
    // @OneToMany(mappedBy = "trabajador")
    // private Set<ServicioTrabajador> servicioTrabajadores;

    // Relación One-to-Many con Horario
    // @OneToMany(mappedBy = "trabajador")
    // private Set<Horario> horarios;

    public Trabajador() {
    }

    public Trabajador(int id, String nombre, String correo, String telefono, boolean estado, String redSocial, LocalDateTime fechaIngreso, String fotoPerfil, int calificacion, String password, String rol) {
        setId(id);
        setNombre(nombre);
        setCorreo(correo);
        setTelefono(telefono);
        setEstado(estado);
        setRedSocial(redSocial);
        setFechaIngreso(fechaIngreso);
        setFotoPerfil(fotoPerfil);
        setCalificacion(calificacion);
        setPassword(password);
        setRol(rol);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID del trabajador no puede ser negativo.");
        }
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del trabajador no puede estar vacío.");
        }
        if (nombre.length() > 100) {
            throw new IllegalArgumentException("El nombre del trabajador es demasiado largo (máx. 100 caracteres).");
        }
        this.nombre = nombre.trim();
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo == null || !correo.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new IllegalArgumentException("El formato del correo electrónico no es válido.");
        }
        this.correo = correo.trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || !telefono.matches("^\\+?[0-9\\s\\-]{7,15}$")) {
            throw new IllegalArgumentException("El teléfono no es válido.");
        }
        this.telefono = telefono;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getRedSocial() {
        return redSocial;
    }

    public void setRedSocial(String redSocial) {
        if (redSocial != null && redSocial.length() > 255) {
            throw new IllegalArgumentException("La URL de la red social es demasiado larga (máx. 255 caracteres).");
        }
        this.redSocial = redSocial;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        if (fechaIngreso == null) {
            throw new IllegalArgumentException("La fecha de ingreso no puede ser nula.");
        }
        if (fechaIngreso.isAfter(LocalDateTime.now().plusMinutes(5))) { // Pequeño margen
            throw new IllegalArgumentException("La fecha de ingreso no puede ser en el futuro.");
        }
        this.fechaIngreso = fechaIngreso;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        if (fotoPerfil != null && fotoPerfil.length() > 255) {
            throw new IllegalArgumentException("La ruta de la foto de perfil es demasiado larga (máx. 255 caracteres).");
        }
        this.fotoPerfil = fotoPerfil;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        if (calificacion < 0 || calificacion > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 0 y 5.");
        }
        this.calificacion = calificacion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        }
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        if (rol == null || rol.trim().isEmpty()) {
            throw new IllegalArgumentException("El rol del trabajador no puede estar vacío.");
        }
        if (rol.length() > 50) {
            throw new IllegalArgumentException("El rol del trabajador es demasiado largo (máx. 50 caracteres).");
        }
        this.rol = rol.trim();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trabajador trabajador = (Trabajador) o;
        return id == trabajador.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Trabajador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", estado=" + estado +
                ", rol='" + rol + '\'' +
                '}';
    }
}
