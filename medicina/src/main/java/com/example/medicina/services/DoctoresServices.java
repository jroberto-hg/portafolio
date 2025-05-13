package com.example.medicina.services;

import com.example.medicina.models.DoctoresModels;
import com.example.medicina.repositories.DoctorRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctoresServices {
    private final DoctorRepositories doctorRepositories;

    @Autowired
    public DoctoresServices(DoctorRepositories doctorRepositorio) {
        this.doctorRepositories = doctorRepositorio;
    }

    public List<DoctoresModels> obtenerTodosDoctores() {
        return doctorRepositories.findAll();
    }

    public Optional<DoctoresModels> obtenerDoctorPorId(Long id) {
        return doctorRepositories.findById(id);
    }

    public DoctoresModels guardarDoctor(DoctoresModels doctor) {
        return doctorRepositories.save(doctor);
    }

    public void eliminarDoctor(Long id) {
        doctorRepositories.deleteById(id);
    }
}
