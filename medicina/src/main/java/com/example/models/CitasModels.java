package com.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "citas")
public class CitasModels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCitas;

    @ManyToOne
    @JoinColumn(name = "consultorio_id", nullable = false)
    private ConsultoriosModels consultorio;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctoresModels doctor;

    @Column(nullable = false, name = "horario_consulta")
    private LocalDateTime horarioConsulta;

    @Column(nullable = false, name = "nombre_paciente")
    private String nombrePaciente;

    public CitasModels(ConsultoriosModels consultorio, DoctoresModels doctor, LocalDateTime horarioConsulta, String nombrePaciente) {
        this.consultorio = consultorio;
        this.doctor = doctor;
        this.horarioConsulta = horarioConsulta;
        this.nombrePaciente = nombrePaciente;
    }
}
