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
@Table(name = "doctores")
public class DoctoresModels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDoctores;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, name = "apellido_paterno")
    private String apellidoPaterno;

    @Column(nullable = false, name = "apellido_materno")
    private String apellidoMaterno;

    @Column(nullable = false, name = "especialidad")
    private String especialidad;

    @OneToMany(mappedBy = "doctor")
    private List<CitasModels> citas;


}
