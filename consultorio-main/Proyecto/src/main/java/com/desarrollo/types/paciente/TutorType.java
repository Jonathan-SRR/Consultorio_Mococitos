package com.desarrollo.types.paciente;

/**
 * @author anny.cont
 */
public class TutorType {

    private Integer id_tutor;
    private String nombre_tutor;
    private String apellido_paterno;
    private String apellido_materno;
    private String correo;
    private String telefono;
    private Boolean estatus;
    private Integer id_paciente;

    public Integer getId_tutor() { return id_tutor; }
    public void setId_tutor(Integer id_tutor) { this.id_tutor = id_tutor; }

    public String getNombre_tutor() { return nombre_tutor; }
    public void setNombre_tutor(String nombre_tutor) { this.nombre_tutor = nombre_tutor; }

    public String getApellido_paterno() { return apellido_paterno; }
    public void setApellido_paterno(String apellido_paterno) { this.apellido_paterno = apellido_paterno; }

    public String getApellido_materno() { return apellido_materno; }
    public void setApellido_materno(String apellido_materno) { this.apellido_materno = apellido_materno; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Boolean getEstatus() { return estatus; }
    public void setEstatus(Boolean estatus) { this.estatus = estatus; }

    public Integer getId_paciente() { return id_paciente; }
    public void setId_paciente(Integer id_paciente) { this.id_paciente = id_paciente; }
}

