package com.example.medicina.controllers;

import com.example.medicina.models.ConsultoriosModels;
import com.example.medicina.services.ConsultoriosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
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
