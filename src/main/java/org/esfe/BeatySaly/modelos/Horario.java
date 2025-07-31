package org.esfe.BeatySaly.modelos;

import java.time.LocalTime;
import java.util.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "horarios")
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private int id;

    // Relación de Muchos a Uno con Trabajador.
    // Usamos @JoinColumn para especificar la clave foránea.
    @ManyToOne
    @JoinColumn(name = "id_trabajador", nullable = false)
    private Trabajador trabajador;

    @Column(name = "dia_semana", nullable = false, length = 10)
    private String diaSemana;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    public Horario() {
    }

    // El constructor ahora recibe el objeto Trabajador completo.
    public Horario(int id, Trabajador trabajador, String diaSemana, LocalTime horaInicio, LocalTime horaFin) {
        setId(id);
        setTrabajador(trabajador);
        setDiaSemana(diaSemana);
        setHoraInicio(horaInicio);
        setHoraFin(horaFin);
    }

    // --- Getters y Setters con Validaciones ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID del horario no puede ser negativo.");
        }
        this.id = id;
    }

    // Getter y Setter para el objeto Trabajador
    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        if (trabajador == null) {
            throw new IllegalArgumentException("El trabajador no puede ser nulo.");
        }
        this.trabajador = trabajador;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        if (diaSemana == null || diaSemana.trim().isEmpty()) {
            throw new IllegalArgumentException("El día de la semana no puede estar vacío.");
        }
        if (diaSemana.length() > 10) {
            throw new IllegalArgumentException("El día de la semana es demasiado largo (máx. 10 caracteres).");
        }
        this.diaSemana = diaSemana.trim();
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        if (horaInicio == null) {
            throw new IllegalArgumentException("La hora de inicio no puede ser nula.");
        }
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        if (horaFin == null) {
            throw new IllegalArgumentException("La hora de fin no puede ser nula.");
        }
        if (horaInicio != null && (horaFin.isBefore(horaInicio) || horaFin.equals(horaInicio))) {
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio.");
        }
        this.horaFin = horaFin;
    }

    // --- equals(), hashCode(), toString() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horario horario = (Horario) o;
        return id == horario.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Horario{" +
                "id=" + id +
                ", idTrabajador=" + (trabajador != null ? trabajador.getId() : "null") + // Muestra el ID del trabajador
                ", diaSemana='" + diaSemana + '\'' +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                '}';
    }
}
