package org.esfe.BeatySaly.modelos;

import java.util.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "administradores")
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_administrador")
    private int id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo", unique = true, nullable = false, length = 100)
    private String correo;

    @Column(name = "telefono", nullable = false)
    private int telefono;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "rol", nullable = false, length = 50)
    private String rol;

    public Administrador() {
    }


    public Administrador(int id, String nombre, String correo, int telefono, String password, String rol) {
        setId(id);
        setNombre(nombre);
        setCorreo(correo);
        setTelefono(telefono);
        setPassword(password);
        setRol(rol);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID del administrador no puede ser negativo.");
        }
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del administrador no puede estar vacío.");
        }
        if (nombre.length() > 100) {
            throw new IllegalArgumentException("El nombre del administrador es demasiado largo (máx. 100 caracteres).");
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

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {

        if (telefono <= 0) {
            throw new IllegalArgumentException("El teléfono no puede ser un valor negativo o cero.");
        }

        this.telefono = telefono;
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
            throw new IllegalArgumentException("El rol del administrador no puede estar vacío.");
        }
        if (rol.length() > 50) {
            throw new IllegalArgumentException("El rol del administrador es demasiado largo (máx. 50 caracteres).");
        }
        this.rol = rol.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administrador that = (Administrador) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono=" + telefono + // Cambio aquí para imprimir como int
                ", rol='" + rol + '\'' +
                '}';
    }
}
