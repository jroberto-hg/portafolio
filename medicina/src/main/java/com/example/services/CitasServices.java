package com.example.services;

import com.example.models.CitasModels;
import com.example.models.ConsultoriosModels;
import com.example.models.DoctoresModels;
import com.example.repositories.CitasRepositories;
import com.example.repositories.ConsultoriosRepositories;
import com.example.repositories.DoctorRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitasServices {
    private final CitasRepositories citaRepositorio;
    private final DoctorRepositories doctorRepositorio;
    private final ConsultoriosRepositories consultorioRepositorio;

    @Autowired
    public CitasServices(CitasRepositories citaRepositorio, DoctorRepositories doctorRepositorio,
                         ConsultoriosRepositories consultorioRepositorio) {
        this.citaRepositorio = citaRepositorio;
        this.doctorRepositorio = doctorRepositorio;
        this.consultorioRepositorio = consultorioRepositorio;
    }

    public CitasModels agendarCita(Long consultorioId, Long doctorId, LocalDateTime horarioConsulta, String nombrePaciente) {
        ConsultoriosModels consultorio = consultorioRepositorio.findById(consultorioId)
                .orElseThrow(() -> new IllegalArgumentException("Consultorio no encontrado"));
        DoctoresModels doctor = doctorRepositorio.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor no encontrado"));

        if (citaRepositorio.existsByConsultorioAndHorarioConsulta(consultorio, horarioConsulta)) {
            throw new IllegalArgumentException("El consultorio está ocupado a esa hora");
        }
        if (citaRepositorio.existsByDoctorAndHorarioConsulta(doctor, horarioConsulta)) {
            throw new IllegalArgumentException("El doctor ya tiene una cita a esa hora");
        }

        List<CitasModels> citasPacienteMismoDia = citaRepositorio.findByNombrePacienteAndHorarioConsultaBetween(
                nombrePaciente, horarioConsulta.toLocalDate().atStartOfDay(), horarioConsulta.toLocalDate().plusDays(1).atStartOfDay());
        for (CitasModels cita : citasPacienteMismoDia) {
            if (Math.abs(cita.getHorarioConsulta().getHour() - horarioConsulta.getHour()) < 2) {
                throw new IllegalArgumentException("El paciente tiene una cita muy cercana a esta hora");
            }
        }

        List<CitasModels> citasDoctorHoy = citaRepositorio.findByHorarioConsultaBetween(doctor.getIdDoctores(), horarioConsulta.toLocalDate().atStartOfDay(), horarioConsulta.toLocalDate().plusDays(1).atStartOfDay());
        if (citasDoctorHoy.size() >= 8) {
            throw new IllegalArgumentException("El doctor ha alcanzado el límite de 8 citas para hoy");
        }

        CitasModels cita = new CitasModels(consultorio, doctor, horarioConsulta, nombrePaciente);
        return citaRepositorio.save(cita);
    }

    public List<CitasModels> consultarCitas(String fecha, Long consultorioId, Long doctorId) {
        if (fecha != null) {
            LocalDateTime inicioDia = LocalDateTime.parse(fecha + "T00:00:00");
            LocalDateTime finDia = inicioDia.plusDays(1);
            return citaRepositorio.findByHorarioConsultaBetween(inicioDia, finDia);
        } else if (consultorioId != null) {
            ConsultoriosModels consultorio = consultorioRepositorio.findById(consultorioId).orElse(null);
            if(consultorio != null) {
                return citaRepositorio.findByConsultorioAndHorarioConsultaBetween(consultorio, LocalDateTime.now().toLocalDate().atStartOfDay(), LocalDateTime.now().toLocalDate().plusDays(1).atStartOfDay());
            }
            else{
                return List.of();
            }
        } else if (doctorId != null) {
            DoctoresServices doctor = doctorRepositorio.findById(doctorId).orElse(null);
            if(doctor != null) {
                return citaRepositorio.findByDoctorAndHorarioConsultaBetween(doctor, LocalDateTime.now().toLocalDate().atStartOfDay(), LocalDateTime.now().toLocalDate().plusDays(1).atStartOfDay());
            }
            else{
                return List.of();
            }
        } else {
            return citaRepositorio.findAll();
        }
    }

    public void cancelarCita(Long id) {
        citaRepositorio.deleteById(id);
    }

    public CitasModels editarCita(Long id, Long consultorioId, Long doctorId, LocalDateTime horarioConsulta, String nombrePaciente) {
        CitasModels cita = citaRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));

        ConsultoriosModels consultorio = consultorioRepositorio.findById(consultorioId)
                .orElseThrow(() -> new IllegalArgumentException("Consultorio no encontrado"));
        DoctoresModels doctor = doctorRepositorio.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor no encontrado"));


        if (cita.getHorarioConsulta() != horarioConsulta) {
            if (citaRepositorio.existsByConsultorioAndHorarioConsulta(consultorio, horarioConsulta) ) {
                throw new IllegalArgumentException("El consultorio está ocupado a esa hora");
            }
            if (citaRepositorio.existsByDoctorAndHorarioConsulta(doctor, horarioConsulta)) {
                throw new IllegalArgumentException("El doctor ya tiene una cita a esa hora");
            }

            List<CitasModels> citasPacienteMismoDia = citaRepositorio.findByNombrePacienteAndHorarioConsultaBetween(
                    nombrePaciente, horarioConsulta.toLocalDate().atStartOfDay(), horarioConsulta.toLocalDate().plusDays(1).atStartOfDay());
            for (CitasModels otraCita : citasPacienteMismoDia) {
                if (!otraCita.getIdCitas().equals(id) && Math.abs(otraCita.getHorarioConsulta().getHour() - horarioConsulta.getHour()) < 2) {
                    throw new IllegalArgumentException("El paciente tiene una cita muy cercana a esta hora");
                }
            }

            List<CitasModels> citasDoctorHoy = citaRepositorio.findByHorarioConsultaBetween(doctor.getId(), horarioConsulta.toLocalDate().atStartOfDay(), horarioConsulta.toLocalDate().plusDays(1).atStartOfDay());
            int citasDelDoctorSinEditar = citasDoctorHoy.size();
            if(cita.getDoctor().getCitas() == doctor.getCitas()){
                citasDelDoctorSinEditar = citasDoctorHoy.size() -1;
            }
            if (citasDelDoctorSinEditar >= 8) {
                throw new IllegalArgumentException("El doctor ha alcanzado el límite de 8 citas para hoy");
            }
        }

        cita.setConsultorio(consultorio);
        cita.setDoctor(doctor);
        cita.setHorarioConsulta(horarioConsulta);
        cita.setNombrePaciente(nombrePaciente);
        return citaRepositorio.save(cita);
    }
}
