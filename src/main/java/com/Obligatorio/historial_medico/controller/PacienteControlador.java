package com.Obligatorio.historial_medico.controller;

import com.Obligatorio.historial_medico.model.Paciente;
import com.Obligatorio.historial_medico.repositorio.pacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteControlador {

    @Autowired
    private pacienteRepository pacienteRepository;

    // Obtener todos los pacientes
    @GetMapping
    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    // Obtener un paciente por CI
    @GetMapping("/{ci}")
    public Paciente getPacienteByCi(@PathVariable String ci) {
        return pacienteRepository.findById(ci).orElse(null);
    }

    // Crear un nuevo paciente
    @PostMapping
    public Paciente createPaciente(@RequestBody Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    // Actualizar un paciente existente
    @PutMapping("/{ci}")
    public Paciente updatePaciente(@PathVariable String ci, @RequestBody Paciente paciente) {
        Paciente existingPaciente = pacienteRepository.findById(ci).orElse(null);
        if (existingPaciente != null) {
            existingPaciente.setNombre(paciente.getNombre());
            existingPaciente.setApellido(paciente.getApellido());
            existingPaciente.setFechaNacimiento(paciente.getFechaNacimiento());
            existingPaciente.setSexo(paciente.getSexo());
            return pacienteRepository.save(existingPaciente);
        }
        return null;
    }

    // Eliminar un paciente
    @DeleteMapping("/{ci}")
    public void deletePaciente(@PathVariable String ci) {
        pacienteRepository.deleteById(ci);
    }
}
