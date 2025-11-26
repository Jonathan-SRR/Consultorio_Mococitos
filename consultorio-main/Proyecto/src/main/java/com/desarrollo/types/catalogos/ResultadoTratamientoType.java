package com.desarrollo.types.catalogos;

import java.time.LocalDate;

public class ResultadoTratamientoType {
    private Integer idRegistro;
    private LocalDate fechaLogDomingo;
    private LocalDate getFechaLogJueves;
    private String observaciones;
    private Integer historialClinico;

    public Integer getIdRegistro() { return idRegistro; }

    public void setIdRegistro(Integer idRegistro) { this.idRegistro = idRegistro; }

    public LocalDate getFechaLogDomingo() { return fechaLogDomingo; }

    public void setFechaLogDomingo(LocalDate fechaLogDomingo) { this.fechaLogDomingo = fechaLogDomingo; }

    public LocalDate getGetFechaLogJueves() { return getFechaLogJueves; }

    public void setGetFechaLogJueves(LocalDate getFechaLogJueves) { this.getFechaLogJueves = getFechaLogJueves; }

    public String getObservaciones() { return observaciones; }

    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Integer getHistorialClinico() { return historialClinico; }

    public void setHistorialClinico(Integer historialClinico) { this.historialClinico = historialClinico; }
}
