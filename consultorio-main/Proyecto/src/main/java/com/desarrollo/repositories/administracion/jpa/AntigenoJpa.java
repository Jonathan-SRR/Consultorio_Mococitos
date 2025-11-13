package com.desarrollo.repositories.administracion.jpa;

import com.desarrollo.entities.administracion.Antigeno;
import com.desarrollo.repositories.DesarrolloJpa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface AntigenoJpa extends DesarrolloJpa<Antigeno, Integer> {

    List<Antigeno> findByCategoria(String categoria);

    List<Antigeno> findByFechaCaducidadBefore(LocalDate fecha);

    List<Antigeno> findByNombreAntigenoContaining(String nombre);

    List<Antigeno> findByLote(String lote);

    @Query("SELECT a FROM Antigeno a WHERE a.fechaCaducidad < CURRENT_DATE")
    List<Antigeno> findCaducados();

    Optional<Antigeno> findByNombreAntigeno(String nombreAntigeno);

}