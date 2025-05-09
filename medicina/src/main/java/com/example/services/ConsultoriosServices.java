package com.example.services;

import com.example.models.ConsultoriosModels;
import com.example.repositories.ConsultoriosRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultoriosServices {
    private final ConsultoriosRepositories consultorioRepositories;

    @Autowired
    public ConsultoriosServices(ConsultoriosRepositories consultorioRepositorio) {
        this.consultorioRepositories = consultorioRepositorio;
    }

    public List<ConsultoriosModels> obtenerTodosConsultorios() {
        return consultorioRepositories.findAll();
    }

    public Optional<ConsultoriosModels> obtenerConsultorioPorId(Long id) {
        return consultorioRepositories.findById(id);
    }

    public ConsultoriosModels guardarConsultorio(ConsultoriosModels consultorio) {
        return consultorioRepositories.save(consultorio);
    }

    public void eliminarConsultorio(Long id) {
        consultorioRepositories.deleteById(id);
    }
}
