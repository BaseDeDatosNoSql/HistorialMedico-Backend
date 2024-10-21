package com.Obligatorio.historial_medico.controller;

import com.Obligatorio.historial_medico.model.registroMedico;
import com.Obligatorio.historial_medico.repositorio.registroMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registros")
public class RegistroMedicoControlador {

    @Autowired
    private registroMedicoRepository registroMedicoRepository;

    // Obtener todos los registros médicos
    @GetMapping
    public List<registroMedico> getAllRegistros() {
        return registroMedicoRepository.findAll();
    }

    // Obtener registros médicos por CI de paciente
    @GetMapping("/paciente/{ciPaciente}")
    public List<registroMedico> getRegistrosByCiPaciente(@PathVariable String ciPaciente) {
        return registroMedicoRepository.findByCiPaciente(ciPaciente);
    }

    // Crear un nuevo registro médico
    @PostMapping
    public registroMedico createRegistro(@RequestBody registroMedico registroMedico) {
        return registroMedicoRepository.save(registroMedico);
    }

    // Eliminar un registro médico por ID
    @DeleteMapping("/{id}")
    public void deleteRegistro(@PathVariable String id) {
        registroMedicoRepository.deleteById(id);
    }
}
