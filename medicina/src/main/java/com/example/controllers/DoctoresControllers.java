package com.example.controllers;

import com.example.models.DoctoresModels;
import com.example.services.DoctoresServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctores")
public class DoctoresControllers {
    private final DoctoresServices doctorServicio;

    @Autowired
    public DoctoresControllers(DoctoresServices doctorServicio) {
        this.doctorServicio = doctorServicio;
    }

    @GetMapping
    public ResponseEntity<List<DoctoresModels>> obtenerTodosLosDoctores() {
        return new ResponseEntity<>(doctorServicio.obtenerTodosDoctores(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctoresModels> obtenerDoctorPorId(@PathVariable Long id) {
        Optional<DoctoresModels> doctor = doctorServicio.obtenerDoctorPorId(id);
        return doctor.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<DoctoresModels> crearDoctor(@RequestBody DoctoresModels doctor) {
        return new ResponseEntity<>(doctorServicio.guardarDoctor(doctor), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDoctor(@PathVariable Long id) {
        doctorServicio.eliminarDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
