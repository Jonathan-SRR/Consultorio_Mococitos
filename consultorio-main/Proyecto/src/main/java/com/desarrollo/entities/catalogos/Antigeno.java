package com.desarrollo.entities.catalogos;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author anny.cont
 */
@Entity
public class Antigeno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombreAntigeno;
    private LocalDate fechaCaducidad;
    private String categoria;
    private Double cantidad;
    private String lote;
    private String permiso;

    public Integer getId(){return id; }
    public void setId(Integer id){ this.id = id; }

    public String getNombreAntigeno(){return nombreAntigeno; }
    public void setNombreAntigeno(String nombreAntigeno) {this.nombreAntigeno = nombreAntigeno;}
    public LocalDate getFechaCaducidad(){return fechaCaducidad; }
    public void setFechaCaducidad(LocalDate fechaCaducidad){this.fechaCaducidad = fechaCaducidad; }

    public String getCategoria(){return categoria; }
    public void setCategoria(String categoria){this.categoria = categoria; }

    public Double getCantidad(){return cantidad; }
    public void setCantidad(Double cantidad){this.cantidad = cantidad;}

    public String getLote(){return lote; }
    public void setLote(String lote){this.lote = lote; }

    public String getPermiso(){return permiso; }
    public void setPermiso(String permiso){this.permiso = permiso; }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Antigeno antigeno = (Antigeno) o;
        return Objects.equals(getId(), antigeno.getId());
    }

    @Override
    public int hashCode() { return Objects.hash( getId() ); }

}
