package com.example.controllers;

import com.example.models.ConsultoriosModels;
import com.example.repositories.ConsultoriosRepositories;
import com.example.services.ConsultoriosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/consultorios")
public class ConsultoriosControllers {
    private final ConsultoriosServices consultorioServicio;

    @Autowired
    public ConsultoriosControllers(ConsultoriosServices consultorioServicio) {
        this.consultorioServicio = consultorioServicio;
    }

    @GetMapping
    public ResponseEntity<List<ConsultoriosModels>> obtenerTodosLosConsultorios() {
        return new ResponseEntity<>(consultorioServicio.obtenerTodosConsultorios(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultoriosModels> obtenerConsultorioPorId(@PathVariable Long id) {
        Optional<ConsultoriosModels> consultorio = consultorioServicio.obtenerConsultorioPorId(id);
        return consultorio.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
