package com.Obligatorio.historial_medico.controller;

import com.Obligatorio.historial_medico.model.Paciente;
import com.Obligatorio.historial_medico.repositorio.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteControlador {

    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping
    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    @GetMapping("/{ci}")
    public ResponseEntity<?> getPacienteByCi(@PathVariable String ci) {
        Paciente paciente = pacienteRepository.findById(ci).orElse(null);
        if (paciente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un paciente con la cédula proporcionada");
        }
        return ResponseEntity.ok(paciente);
    }

    @PostMapping
    public ResponseEntity<String> agregarPaciente(@RequestBody Paciente paciente) {
        if (pacienteRepository.existsById(paciente.getCi())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("El paciente ya existe");
        }
        pacienteRepository.save(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Paciente agregado correctamente");
    }

    @PutMapping("/{ci}")
    public ResponseEntity<?> actualizarPaciente(@PathVariable String ci, @RequestBody Paciente paciente) {
        Paciente existingPaciente = pacienteRepository.findById(ci).orElse(null);
        if (existingPaciente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un paciente con la cédula proporcionada");
        }
        existingPaciente.setNombre(paciente.getNombre());
        existingPaciente.setApellido(paciente.getApellido());
        existingPaciente.setFechaNacimiento(paciente.getFechaNacimiento());
        existingPaciente.setSexo(paciente.getSexo());
        pacienteRepository.save(existingPaciente);
        return ResponseEntity.ok("Paciente actualizado correctamente");
    }

    @DeleteMapping("/{ci}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable String ci) {
        if (!pacienteRepository.existsById(ci)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un paciente con la cédula proporcionada");
        }
        pacienteRepository.deleteById(ci);
        return ResponseEntity.ok("Paciente eliminado correctamente");
    }
}

