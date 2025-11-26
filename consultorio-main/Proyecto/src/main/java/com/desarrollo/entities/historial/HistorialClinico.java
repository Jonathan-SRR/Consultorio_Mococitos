package com.desarrollo.entities.historial;

import com.desarrollo.entities.pacientes.Pacientes;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class HistorialClinico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHistorialClinico;

    @ManyToOne
    @JoinColumn(name = "idPaciente", nullable = false)
    private Pacientes paciente;

    private LocalDate fechaCreacion;

    private String nombreMedico;

    private String motivoConsulta;

    private LocalDate fechaInicio;

    private String tipoSintoma;

    private String presentacionDurante;

    private String duracion;

    private String tiempoAproximado;

    private String cambiosTiempo;

    private String apareceCuando;

    private String ocupacion;

    private String antecedentes;

    private Boolean animalesEnCasa;

    private Boolean visitaAnimales;

    private Boolean picadura;

    private Boolean reaccion;

    private String explicacionPicadura;

    private String plantasMedicinales;

    private String comentarios;

    private String tratamientosRecibidos;

    private Boolean estatus;

    // Getters y Setters

    public Integer getIdHistorialClinico() {
        return idHistorialClinico;
    }

    public void setIdHistorialClinico(Integer idHistorialClinico) {
        this.idHistorialClinico = idHistorialClinico;
    }

    public Pacientes getPaciente() {
        return paciente;
    }

    public void setPaciente(Pacientes paciente) {
        this.paciente = paciente;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getTipoSintoma() {
        return tipoSintoma;
    }

    public void setTipoSintoma(String tipoSintoma) {
        this.tipoSintoma = tipoSintoma;
    }

    public String getPresentacionDurante() {
        return presentacionDurante;
    }

    public void setPresentacionDurante(String presentacionDurante) {
        this.presentacionDurante = presentacionDurante;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getTiempoAproximado() {
        return tiempoAproximado;
    }

    public void setTiempoAproximado(String tiempoAproximado) {
        this.tiempoAproximado = tiempoAproximado;
    }

    public String getCambiosTiempo() {
        return cambiosTiempo;
    }

    public void setCambiosTiempo(String cambiosTiempo) {
        this.cambiosTiempo = cambiosTiempo;
    }

    public String getApareceCuando() {
        return apareceCuando;
    }

    public void setApareceCuando(String apareceCuando) {
        this.apareceCuando = apareceCuando;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    public Boolean getAnimalesEnCasa() {
        return animalesEnCasa;
    }

    public void setAnimalesEnCasa(Boolean animalesEnCasa) {
        this.animalesEnCasa = animalesEnCasa;
    }

    public Boolean getVisitaAnimales() {
        return visitaAnimales;
    }

    public void setVisitaAnimales(Boolean visitaAnimales) {
        this.visitaAnimales = visitaAnimales;
    }

    public Boolean getPicadura() {
        return picadura;
    }

    public void setPicadura(Boolean picadura) {
        this.picadura = picadura;
    }

    public Boolean getReaccion() {
        return reaccion;
    }

    public void setReaccion(Boolean reaccion) {
        this.reaccion = reaccion;
    }

    public String getExplicacionPicadura() {
        return explicacionPicadura;
    }

    public void setExplicacionPicadura(String explicacionPicadura) {
        this.explicacionPicadura = explicacionPicadura;
    }

    public String getPlantasMedicinales() {
        return plantasMedicinales;
    }

    public void setPlantasMedicinales(String plantasMedicinales) {
        this.plantasMedicinales = plantasMedicinales;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getTratamientosRecibidos() {
        return tratamientosRecibidos;
    }

    public void setTratamientosRecibidos(String tratamientosRecibidos) {
        this.tratamientosRecibidos = tratamientosRecibidos;
    }

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }
}
