package org.esfe.BeatySaly.modelos;

import java.time.LocalDate;
import java.util.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "resennas")
public class Resenna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resenna")
    private int id;

    @Column(name = "id_cliente", nullable = false)
    private int idCliente;

    @Column(name = "id_trabajador", nullable = false)
    private int idTrabajador;

    @Column(name = "calificacion", nullable = false)
    private int calificacion;

    @Column(name = "comentario", length = 1000)
    private String comentario;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    public Resenna() {
    }

    public Resenna(int id, int idCliente, int idTrabajador, int calificacion, String comentario, LocalDate fecha) {
        setId(id);
        setIdCliente(idCliente);
        setIdTrabajador(idTrabajador);
        setCalificacion(calificacion);
        setComentario(comentario);
        setFecha(fecha);
    }

    // --- Getters y Setters con Validaciones ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID de la reseña no puede ser negativo.");
        }
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        if (idCliente <= 0) {
            throw new IllegalArgumentException("El ID del cliente debe ser un número positivo.");
        }
        this.idCliente = idCliente;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        if (idTrabajador <= 0) {
            throw new IllegalArgumentException("El ID del trabajador debe ser un número positivo.");
        }
        this.idTrabajador = idTrabajador;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        if (calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5.");
        }
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        if (comentario != null && comentario.length() > 1000) {
            throw new IllegalArgumentException("El comentario es demasiado largo (máx. 1000 caracteres).");
        }
        this.comentario = comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha de la reseña no puede ser nula.");
        }
        if (fecha.isAfter(LocalDate.now().plusDays(1))) { // Pequeño margen
            throw new IllegalArgumentException("La fecha de la reseña no puede ser en el futuro.");
        }
        this.fecha = fecha;
    }

    // --- equals(), hashCode(), toString() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resenna resenna = (Resenna) o;
        return id == resenna.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Resenna{" +
                "id=" + id +
                ", idCliente=" + idCliente +
                ", idTrabajador=" + idTrabajador +
                ", calificacion=" + calificacion +
                ", fecha=" + fecha +
                '}';
    }
}
