package com.desarrollo.types.paciente;

import java.time.LocalDate;

/**
 * @author anny
 */
public class PacienteType {

    private Integer id_paciente;
    private String nombre_paciente;
    private String apellido_paterno;
    private String apellido_materno;
    private LocalDate fecha_nacimiento;
    private String sexo;
    private Boolean estatus;

    public Integer getId_paciente() { return id_paciente; }
    public void setId_paciente(Integer id_paciente) { this.id_paciente = id_paciente; }

    public String getNombre_paciente() { return nombre_paciente; }
    public void setNombre_paciente(String nombre_paciente) { this.nombre_paciente = nombre_paciente; }

    public String getApellido_paterno() { return apellido_paterno; }
    public void setApellido_paterno(String apellido_paterno) { this.apellido_paterno = apellido_paterno; }

    public String getApellido_materno() { return apellido_materno; }
    public void setApellido_materno(String apellido_materno) { this.apellido_materno = apellido_materno; }

    public LocalDate getFecha_nacimiento() { return fecha_nacimiento; }
    public void setFecha_nacimiento(LocalDate fecha_nacimiento) { this.fecha_nacimiento = fecha_nacimiento; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public Boolean getEstatus() { return estatus; }
    public void setEstatus(Boolean estatus) { this.estatus = estatus; }
}

