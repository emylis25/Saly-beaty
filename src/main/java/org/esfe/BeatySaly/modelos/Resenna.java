package org.esfe.BeatySaly.modelos;

import java.time.LocalDate;
import java.util.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "resennas") // Corregido: "resennas" en lugar de "resennas"
public class Resenna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resenna")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_trabajador", nullable = false)
    private Trabajador trabajador;

    @Column(name = "calificacion", nullable = false)
    private int calificacion;

    @Column(name = "comentario", length = 1000)
    private String comentario;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;


    public Resenna() {
    }

    // El constructor ahora recibe los objetos de las entidades relacionadas.
    public Resenna(int id, Cliente cliente, Trabajador trabajador, int calificacion, String comentario, LocalDate fecha) {
        setId(id);
        setCliente(cliente);
        setTrabajador(trabajador);
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

    // ✅ Método PrePersist para asignar fecha si es null
    @PrePersist
    public void prePersist() {
        if (this.fecha == null) {
            this.fecha = LocalDate.now();
        }
    }

    // Getters y setters para las entidades Cliente y Trabajador.
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        this.cliente = cliente;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        if (trabajador == null) {
            throw new IllegalArgumentException("El trabajador no puede ser nulo.");
        }
        this.trabajador = trabajador;
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
        // Usar la fecha actual para la validación, con un pequeño margen si es necesario.
        if (fecha.isAfter(LocalDate.now().plusDays(1))) {
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
                ", cliente=" + (cliente != null ? cliente.getId() : "null") +
                ", trabajador=" + (trabajador != null ? trabajador.getId() : "null") +
                ", calificacion=" + calificacion +
                ", fecha=" + fecha +
                '}';
    }
}
