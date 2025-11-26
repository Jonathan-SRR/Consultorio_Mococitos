package com.desarrollo.entities.catalogos;

import com.desarrollo.entities.historial.HistorialClinico;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class ResultadoTratamiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRegistro;

    private LocalDate fechaLogDomingo;
    private LocalDate getFechaLogJueves;
    private String observaciones;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idHistorialClinico", nullable = false)
    private HistorialClinico historialClinico;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idEsquema", nullable = false)
    private EsquemaTratamiento esquemaTratamiento;

    public Integer getIdRegistro() { return idRegistro; }

    public void setIdRegistro(Integer idRegistro) { this.idRegistro = idRegistro; }

    public LocalDate getFechaLogDomingo() { return fechaLogDomingo; }

    public void setFechaLogDomingo(LocalDate fechaLogDomingo) { this.fechaLogDomingo = fechaLogDomingo; }

    public LocalDate getGetFechaLogJueves() { return getFechaLogJueves; }

    public void setGetFechaLogJueves(LocalDate getFechaLogJueves) { this.getFechaLogJueves = getFechaLogJueves; }

    public String getObservaciones() { return observaciones; }

    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public HistorialClinico getHistorialClinico() { return historialClinico; }

    public void setHistorialClinico(HistorialClinico historialClinico) { this.historialClinico = historialClinico; }

    public EsquemaTratamiento getEsquemaTratamiento() { return esquemaTratamiento; }

    public void setEsquemaTratamiento(EsquemaTratamiento esquemaTratamiento) { this.esquemaTratamiento = esquemaTratamiento; }

}
