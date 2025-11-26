package com.desarrollo.entities.catalogos;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class EsquemaTratamiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        EsquemaTratamiento esquemaTratamiento = (EsquemaTratamiento) o;
        return Objects.equals(getIdEsquema(), esquemaTratamiento.getIdEsquema());
    }

    @Override
    public int hashCode() { return Objects.hash(getIdEsquema()); }

}
