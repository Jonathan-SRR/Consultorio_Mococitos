package com.desarrollo.types.paciente;

/**
 * @author anny
 */
public class DireccionType {

    private Integer id_direccion;
    private String calle;
    private Integer numero_ext;
    private String colonia;
    private String estado;
    private Integer codigo_postal;
    private Boolean estatus;
    private Integer id_tutor;

    public Integer getId_direccion() { return id_direccion; }
    public void setId_direccion(Integer id_direccion) { this.id_direccion = id_direccion; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public Integer getNumero_ext() { return numero_ext; }
    public void setNumero_ext(Integer numero_ext) { this.numero_ext = numero_ext; }

    public String getColonia() { return colonia; }
    public void setColonia(String colonia) { this.colonia = colonia; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Integer getCodigo_postal() { return codigo_postal; }
    public void setCodigo_postal(Integer codigo_postal) { this.codigo_postal = codigo_postal; }

    public Boolean getEstatus() { return estatus; }
    public void setEstatus(Boolean estatus) { this.estatus = estatus; }

    public Integer getId_tutor() { return id_tutor; }
    public void setId_tutor(Integer id_tutor) { this.id_tutor = id_tutor; }
}

