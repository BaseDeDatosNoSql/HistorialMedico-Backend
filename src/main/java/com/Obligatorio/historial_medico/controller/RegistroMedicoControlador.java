package com.Obligatorio.historial_medico.controller;

import com.Obligatorio.historial_medico.model.RegistroMedico;
import com.Obligatorio.historial_medico.repositorio.RegistroMedicoRepository;
import com.Obligatorio.historial_medico.model.Paciente;
import com.Obligatorio.historial_medico.repositorio.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registros")
public class RegistroMedicoControlador {

    @Autowired
    private RegistroMedicoRepository registroMedicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    // Agregar un nuevo registro médico asociado a un paciente
    @PostMapping("/{ciPaciente}")
    public ResponseEntity<String> agregarRegistroMedico(@PathVariable String ciPaciente, @RequestBody RegistroMedico registroMedico) {
        Paciente paciente = pacienteRepository.findByCi(ciPaciente);
        if (paciente == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe un paciente con la cédula aportada como parámetro");
        }
        registroMedico.setCiPaciente(ciPaciente);
        registroMedicoRepository.save(registroMedico);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registro médico agregado correctamente");
    }

    // Consultar historial médico por CI del paciente, con paginación
    @GetMapping("/historial/{ciPaciente}")
    public ResponseEntity<?> consultarHistorialMedico(@PathVariable String ciPaciente, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Paciente paciente = pacienteRepository.findByCi(ciPaciente);
        if (paciente == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe un paciente con la cédula aportada como parámetro");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("fecha").descending());
        Page<RegistroMedico> registros = registroMedicoRepository.findByCiPaciente(ciPaciente, pageable);
        return ResponseEntity.ok(registros);
    }

    // Obtener registros por criterios
    @GetMapping("/criterios")
    public ResponseEntity<List<RegistroMedico>> obtenerRegistrosPorCriterio(
        @RequestParam(required = false) String tipo,
        @RequestParam(required = false) String diagnostico,
        @RequestParam(required = false) String medico,
        @RequestParam(required = false) String institucion) {
        
        List<RegistroMedico> registros = registroMedicoRepository.findByCriterios(tipo, diagnostico, medico, institucion);
        return ResponseEntity.ok(registros);
    }
}
