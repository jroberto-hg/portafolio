package com.example.medicina.controllers;

import com.example.medicina.models.CitasModels;
import com.example.medicina.services.CitasServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitasController {
    private final CitasServices citaServicio;

    @Autowired
    public CitasController(CitasServices citaServicio) {
        this.citaServicio = citaServicio;
    }

    @PostMapping
    public ResponseEntity<CitasModels> agendarCita(
            @RequestParam Long consultorioId,
            @RequestParam Long doctorId,
            @RequestParam LocalDateTime horarioConsulta,
            @RequestParam String nombrePaciente) {
        try {
            CitasModels nuevaCita = citaServicio.agendarCita(consultorioId, doctorId, horarioConsulta, nombrePaciente);
            return new ResponseEntity<>(nuevaCita, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<CitasModels>> consultarCitas(
            @RequestParam(required = false) String fecha,
            @RequestParam(required = false) Long consultorioId,
            @RequestParam(required = false) Long doctorId) {
        List<CitasModels> citas = citaServicio.consultarCitas(fecha, consultorioId, doctorId);
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarCita(@PathVariable Long id) {
        citaServicio.cancelarCita(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitasModels> editarCita(
            @PathVariable Long id,
            @RequestParam Long consultorioId,
            @RequestParam Long doctorId,
            @RequestParam LocalDateTime horarioConsulta,
            @RequestParam String nombrePaciente) {
        try {
            CitasModels citaEditada = citaServicio.editarCita(id, consultorioId, doctorId, horarioConsulta, nombrePaciente);
            return new ResponseEntity<>(citaEditada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
