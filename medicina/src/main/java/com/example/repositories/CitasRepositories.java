package com.example.repositories;

import com.example.models.CitasModels;
import com.example.models.ConsultoriosModels;
import com.example.models.DoctoresModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitasRepositories extends JpaRepository<CitasModels, Long> {
    boolean existsByConsultorioAndHorarioConsulta(ConsultoriosModels consultorio, LocalDateTime horarioConsulta);

    boolean existsByDoctorAndHorarioConsulta(DoctoresModels doctor, LocalDateTime horarioConsulta);

    List<CitasModels> findByNombrePacienteAndHorarioConsultaBetween(String nombrePaciente, LocalDateTime horaInicio, LocalDateTime horaFin);

    List<CitasModels> findByHorarioConsultaBetween(LocalDateTime inicioDia, LocalDateTime finDia);

    @Query("SELECT c FROM Cita c WHERE c.doctor = ?1 AND c.horarioConsulta BETWEEN ?2 AND ?3")
    List<CitasModels> findByDoctorAndHorarioConsultaBetween(DoctoresModels doctor, LocalDateTime inicioDia, LocalDateTime finDia);

    @Query("SELECT c FROM Cita c WHERE c.consultorio = ?1 AND c.horarioConsulta BETWEEN ?2 AND ?3")
    List<CitasModels> findByConsultorioAndHorarioConsultaBetween(ConsultoriosModels consultorio, LocalDateTime inicioDia, LocalDateTime finDia);
}
