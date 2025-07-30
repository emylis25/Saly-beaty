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

    @Column(name = "id_trabajador", nullable = false)
    private int idTrabajador;

    @Column(name = "dia_semana", nullable = false, length = 10) // "Lunes", "Martes", etc.
    private String diaSemana;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    public Horario() {
    }

    public Horario(int id, int idTrabajador, String diaSemana, LocalTime horaInicio, LocalTime horaFin) {
        setId(id);
        setIdTrabajador(idTrabajador);
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

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        if (idTrabajador <= 0) {
            throw new IllegalArgumentException("El ID del trabajador debe ser un número positivo.");
        }
        this.idTrabajador = idTrabajador;
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
        // Opcional: Validar contra una lista de días válidos
        // List<String> diasValidos = Arrays.asList("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo");
        // if (!diasValidos.contains(diaSemana)) {
        //     throw new IllegalArgumentException("El día de la semana no es válido.");
        // }
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
                ", idTrabajador=" + idTrabajador +
                ", diaSemana='" + diaSemana + '\'' +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                '}';
    }
}
