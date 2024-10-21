package com.Obligatorio.historial_medico.repositorio;
import com.Obligatorio.historial_medico.model.registroMedico;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
public interface registroMedicoRepository extends MongoRepository<registroMedico, String> {
	 List<registroMedico> findByCiPaciente(String ciPaciente);
}
