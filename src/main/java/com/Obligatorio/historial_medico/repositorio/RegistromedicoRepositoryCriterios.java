package com.Obligatorio.historial_medico.repositorio;

import com.Obligatorio.historial_medico.enums.Tipo;
import com.Obligatorio.historial_medico.model.RegistroMedico;

import java.util.List;

public interface RegistromedicoRepositoryCriterios {

    List<RegistroMedico> findByCriterios(Tipo tipo, String diagnostico, String medico, String institucion);


}
