package com.Obligatorio.historial_medico.repositorio;

import com.Obligatorio.historial_medico.enums.Tipo;
import com.Obligatorio.historial_medico.model.RegistroMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegistroMedicoRepositoryImpl implements RegistromedicoRepositoryCriterios{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<RegistroMedico> findByCriterios(Tipo tipo, String diagnostico, String medico, String institucion) {
        Query query = new Query();

        if (tipo != null) {
            query.addCriteria(Criteria.where("tipo").is(tipo));
        }
        if (diagnostico != null && !diagnostico.isEmpty()) {
            query.addCriteria(Criteria.where("diagnostico").regex(diagnostico, "i"));
        }
        if (medico != null && !medico.isEmpty()) {
            query.addCriteria(Criteria.where("medico").regex(medico, "i"));
        }
        if (institucion != null && !institucion.isEmpty()) {
            query.addCriteria(Criteria.where("institucion").regex(institucion, "i"));
        }

        return mongoTemplate.find(query, RegistroMedico.class);
    }


}
