package org.esfe.BeatySaly.modelos;

import java.util.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "servicio_trabajador")
public class ServicioTrabajador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio_trabajador")
    private int id;

    // Puedes usar @ManyToOne si quieres mapear las relaciones directamente aquí
    // @ManyToOne
    // @JoinColumn(name = "id_servicio", nullable = false)
    @Column(name = "id_servicio", nullable = false)
    private int idServicio;

    // @ManyToOne
    // @JoinColumn(name = "id_trabajador", nullable = false)
    @Column(name = "id_trabajador", nullable = false)
    private int idTrabajador;

    public ServicioTrabajador() {
    }

    public ServicioTrabajador(int id, int idServicio, int idTrabajador) {
        setId(id);
        setIdServicio(idServicio);
        setIdTrabajador(idTrabajador);
    }

    // --- Getters y Setters con Validaciones ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID de ServicioTrabajador no puede ser negativo.");
        }
        this.id = id;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        if (idServicio <= 0) {
            throw new IllegalArgumentException("El ID del servicio debe ser un número positivo.");
        }
        this.idServicio = idServicio;
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

    // --- equals(), hashCode(), toString() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServicioTrabajador that = (ServicioTrabajador) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ServicioTrabajador{" +
                "id=" + id +
                ", idServicio=" + idServicio +
                ", idTrabajador=" + idTrabajador +
                '}';
    }
}
