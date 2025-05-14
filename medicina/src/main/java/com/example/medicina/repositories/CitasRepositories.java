package com.example.medicina.repositories;

import com.example.medicina.models.CitasModels;
import com.example.medicina.models.ConsultoriosModels;
import com.example.medicina.models.DoctoresModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitasRepositories extends JpaRepository<CitasModels, Long> {
    boolean existsByConsultorioAndHorarioConsulta(ConsultoriosModels consultorio, LocalDateTime horarioConsulta);

    boolean existsByDoctorAndHorarioConsulta(DoctoresModels doctor, LocalDateTime horarioConsulta);

    List<CitasModels> findByNombrePacienteAndHorarioConsultaBetween(String nombrePaciente, LocalDateTime horaInicio, LocalDateTime horaFin);

    List<CitasModels> findByHorarioConsultaBetween(LocalDateTime inicioDia, LocalDateTime finDia);

    @Query(
            value = "SELECT c.id, c.horario_consulta, c.nombre_paciente, c.consultorio, c.doctor FROM citas c WHERE c.doctor = ?1 AND c.horarioConsulta BETWEEN ?2 AND ?3",
            nativeQuery = true
    )
    List<CitasModels> findByDoctorAndHorarioConsultaBetween(DoctoresModels doctor, LocalDateTime inicioDia, LocalDateTime finDia);

    @Query(
            value = "SELECT c.id, c.horario_consulta, c.nombre_paciente, c.consultorio, c.doctor FROM citas c WHERE c.consultorio = ?1 AND c.horarioConsulta BETWEEN ?2 AND ?3",
            nativeQuery = true
    )
    List<CitasModels> findByConsultorioAndHorarioConsultaBetween(ConsultoriosModels consultorio, LocalDateTime inicioDia, LocalDateTime finDia);
}
