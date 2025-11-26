package com.desarrollo.types.catalogos;

import java.math.BigDecimal;

public class EsquemaTratamientoType {

    private Integer idEsquema;
    private String semana;
    private BigDecimal dosis;
    private String descripcion;

    public Integer getIdEsquema() { return idEsquema; }

    public void setIdEsquema(Integer idEsquema) { this.idEsquema = idEsquema; }

    public String getSemana() { return semana; }

    public void setSemana(String semana) { this.semana = semana; }

    public BigDecimal getDosis() { return dosis; }

    public void setDosis(BigDecimal dosis) { this.dosis = dosis; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
