package com.Obligatorio.historial_medico.repositorio;

import com.Obligatorio.historial_medico.model.Paciente;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PacienteRepository extends MongoRepository<Paciente, String> {
    List<Paciente> findByNombre(String nombre);

    Paciente findByCi(String ci);
}
