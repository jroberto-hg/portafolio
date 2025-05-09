package com.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "consultorios")
public class ConsultoriosModels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsultorio;

    @Column(nullable = false, name = "numero_consultorio")
    private String numeroConsultorio;

    @Column(nullable = false)
    private String piso;

    @OneToMany(mappedBy = "consultorio")
    private List<CitasModels> citas;
}
