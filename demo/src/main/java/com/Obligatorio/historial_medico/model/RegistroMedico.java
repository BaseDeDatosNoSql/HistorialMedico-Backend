package com.Obligatorio.historial_medico.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "registros_medicos")
public class RegistroMedico {

    @Id
    private String id;
    private String ciPaciente;
    private String fecha;
    private String tipo;
    private String diagnostico;
    private String medico;
    private String institucion;
    private String descripcion;
    private String medicacion;

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCiPaciente() { return ciPaciente; }
    public void setCiPaciente(String ciPaciente) { this.ciPaciente = ciPaciente; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getMedico() { return medico; }
    public void setMedico(String medico) { this.medico = medico; }

    public String getInstitucion() { return institucion; }
    public void setInstitucion(String institucion) { this.institucion = institucion; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getMedicacion() { return medicacion; }
    public void setMedicacion(String medicacion) { this.medicacion = medicacion; }
}
