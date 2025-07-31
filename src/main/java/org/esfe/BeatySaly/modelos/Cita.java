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

    // Relaciones de Muchos a Uno. JPA se encargará de gestionar las claves foráneas.
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    // Nota: El campo "telefono" es un atributo de la Cita, no de la relación.
    @Column(name = "telefono", length = 15)
    private String telefono; // Recomendado usar String para teléfonos.

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "id_trabajador", nullable = false)
    private Trabajador trabajador;

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    public Cita() {
    }

    // El constructor también debe usar los objetos, no los IDs.
    public Cita(int id, Cliente cliente, String telefono, LocalDateTime fechaHora, Trabajador trabajador, Servicio servicio) {
        setId(id);
        setCliente(cliente);
        setTelefono(telefono);
        setFechaHora(fechaHora);
        setTrabajador(trabajador);
        setServicio(servicio);
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

    // Los getters y setters ahora manipulan los objetos de las entidades.
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente de la cita no puede ser nulo.");
        }
        this.cliente = cliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío.");
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
        if (fechaHora.isBefore(LocalDateTime.now().minusMinutes(5))) {
            throw new IllegalArgumentException("La fecha y hora de la cita no pueden ser en el pasado.");
        }
        this.fechaHora = fechaHora;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        if (trabajador == null) {
            throw new IllegalArgumentException("El trabajador de la cita no puede ser nulo.");
        }
        this.trabajador = trabajador;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        if (servicio == null) {
            throw new IllegalArgumentException("El servicio de la cita no puede ser nulo.");
        }
        this.servicio = servicio;
    }

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
                ", cliente=" + (cliente != null ? cliente.getId() : "null") + // Muestra el ID del cliente
                ", telefono='" + telefono + '\'' +
                ", fechaHora=" + fechaHora +
                ", trabajador=" + (trabajador != null ? trabajador.getId() : "null") + // Muestra el ID del trabajador
                ", servicio=" + (servicio != null ? servicio.getId() : "null") + // Muestra el ID del servicio
                '}';
    }
}
