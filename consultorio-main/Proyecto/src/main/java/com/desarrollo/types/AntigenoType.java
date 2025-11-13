package com.desarrollo.types;

import java.time.LocalDate;

public class AntigenoType {

    private Integer id;
    private String nombre_antigeno;
    private LocalDate fecha_caducidad;
    private String categoria;
    private Double cantidad;
    private String lote;
    private String permiso;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre_antigeno() { return nombre_antigeno; }
    public void setNombre_antigeno(String nombre_antigeno) { this.nombre_antigeno = nombre_antigeno; }

    public LocalDate getFecha_caducidad() { return fecha_caducidad; }
    public void setFecha_caducidad(LocalDate fecha_caducidad) { this.fecha_caducidad = fecha_caducidad; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Double getCantidad() { return cantidad; }
    public void setCantidad(Double cantidad) { this.cantidad = cantidad; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public String getPermiso() { return permiso; }
    public void setPermiso(String permiso) { this.permiso = permiso; }
}
