package com.desarrollo.entities.pacientes;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "direccion") // Asegurar que el nombre de la tabla sea correcto
public class Direccion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDireccion;

    private String calle;
    private Integer numeroExt;
    private String colonia;
    private String estado;
    private Integer codigoPostal;
    private Boolean estatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTutor", nullable = false) // Asegurar que el nombre de la columna sea correcto
    private Tutor tutor;

    public Integer getIdDireccion() { return idDireccion; }
    public void setIdDireccion(Integer idDireccion) { this.idDireccion = idDireccion; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public Integer getNumeroExt() { return numeroExt; }
    public void setNumeroExt(Integer numeroExt) { this.numeroExt = numeroExt; }

    public String getColonia() { return colonia; }
    public void setColonia(String colonia) { this.colonia = colonia; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Integer getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(Integer codigoPostal) { this.codigoPostal = codigoPostal; }

    public Boolean getEstatus() { return estatus; }
    public void setEstatus(Boolean estatus) { this.estatus = estatus; }

    public Tutor getTutor() { return tutor; }
    public void setTutor(Tutor tutor) { this.tutor = tutor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direccion that = (Direccion) o;
        return Objects.equals(idDireccion, that.idDireccion);
    }

    @Override
    public int hashCode() { return Objects.hash(idDireccion);}

    @Override
    public String toString() {
        return "Direccion{" +
                "idDireccion=" + idDireccion +
                ", calle='" + calle + '\'' +
                ", numeroExt=" + numeroExt +
                ", colonia='" + colonia + '\'' +
                ", estado='" + estado + '\'' +
                ", codigoPostal=" + codigoPostal +
                ", estatus=" + estatus +
                ", tutor=" + (tutor != null ? tutor.getIdTutor() : "NULL") +
                '}';
    }
}