package org.esfe.BeatySaly.modelos;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "servicios")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private int id;

    @Column(name = "nombre_servicio", nullable = false, length = 100)
    private String nombreServicio;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "duracion", nullable = false)
//    @Convert(converter = DurationConverter.class)
    private Duration duracion;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    public Servicio() {
    }

    public Servicio(int id, String nombreServicio, String descripcion, Duration duracion, BigDecimal precio) {
        setId(id);
        setNombreServicio(nombreServicio);
        setDescripcion(descripcion);
        setDuracion(duracion);
        setPrecio(precio);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID del servicio no puede ser negativo.");
        }
        this.id = id;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        if (nombreServicio == null || nombreServicio.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del servicio no puede estar vacío.");
        }
        if (nombreServicio.length() > 100) {
            throw new IllegalArgumentException("El nombre del servicio es demasiado largo (máx. 100 caracteres).");
        }
        this.nombreServicio = nombreServicio.trim();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion != null && descripcion.length() > 500) {
            throw new IllegalArgumentException("La descripción del servicio es demasiado larga (máx. 500 caracteres).");
        }
        this.descripcion = descripcion;
    }

    public Duration getDuracion() {
        return duracion;
    }

    public void setDuracion(Duration duracion) {
        if (duracion == null) {
            throw new IllegalArgumentException("La duración del servicio no puede ser nula.");
        }
        if (duracion.isNegative() || duracion.isZero()) {
            throw new IllegalArgumentException("La duración del servicio debe ser positiva.");
        }
        this.duracion = duracion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        if (precio == null) {
            throw new IllegalArgumentException("El precio del servicio no puede ser nulo.");
        }
        if (precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio del servicio no puede ser negativo.");
        }
        this.precio = precio;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servicio servicio = (Servicio) o;
        return id == servicio.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "id=" + id +
                ", nombreServicio='" + nombreServicio + '\'' +
                ", duracion=" + duracion +
                ", precio=" + precio +
                '}';
    }
}