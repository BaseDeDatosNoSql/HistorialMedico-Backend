package com.Obligatorio.historial_medico.repositorio;

import com.Obligatorio.historial_medico.model.RegistroMedico;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RegistroMedicoRepository extends MongoRepository<RegistroMedico, String> {
    Page<RegistroMedico> findByCiPaciente(String ciPaciente, Pageable pageable);

    @Query("{ 'tipo': ?0, 'diagnostico': ?1, 'medico': ?2, 'institucion': ?3 }")
    List<RegistroMedico> findByCriterios(String tipo, String diagnostico, String medico, String institucion);
}
