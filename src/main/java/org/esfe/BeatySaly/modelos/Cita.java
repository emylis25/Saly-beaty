package org.esfe.BeatySaly.modelos;

import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private int id;

    @Column(name = "id_cliente", nullable = false)
    private int idCliente;

    @Column(name = "telefono", length = 15)
    private int telefono;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "id_trabajador", nullable = false)
    private int idTrabajador;

    @Column(name = "id_servicio", nullable = false)
    private int idServicio;

    public Cita() {
    }

    public Cita(int id, int idCliente, int telefono, LocalDateTime fechaHora, int idTrabajador, int idServicio) {
        setId(id);
        setIdCliente(idCliente);
        setTelefono(telefono);
        setFechaHora(fechaHora);
        setIdTrabajador(idTrabajador);
        setIdServicio(idServicio);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID de la cita no puede ser negativo.");
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

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        if (telefono <= 0) {
            throw new IllegalArgumentException("El teléfono no puede ser un valor negativo o cero.");
        }

        this.telefono = telefono;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha y hora de la cita no pueden ser nulas.");
        }
        if (fechaHora.isBefore(LocalDateTime.now().minusMinutes(5))) { // Pequeño margen para la hora actual
            throw new IllegalArgumentException("La fecha y hora de la cita no pueden ser en el pasado.");
        }
        this.fechaHora = fechaHora;
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

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        if (idServicio <= 0) {
            throw new IllegalArgumentException("El ID del servicio debe ser un número positivo.");
        }
        this.idServicio = idServicio;
    }

    // --- equals(), hashCode(), toString() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cita cita = (Cita) o;
        return id == cita.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", idCliente=" + idCliente +
                ", telefono='" + telefono + '\'' +
                ", fechaHora=" + fechaHora +
                ", idTrabajador=" + idTrabajador +
                ", idServicio=" + idServicio +
                '}';
    }
}
