package com.desarrollo.repositories.paciente.jpa;

import com.desarrollo.entities.pacientes.Direccion;
import com.desarrollo.repositories.DesarrolloJpa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DireccionJpa extends DesarrolloJpa<Direccion, Integer> {

    List<Direccion> findByCalleContaining(String calle);

    List<Direccion> findByColoniaContaining(String colonia);

    List<Direccion> findByEstado(String estado);

    List<Direccion> findByCodigoPostal(Integer codigoPostal);

    List<Direccion> findByTutorIdTutor(Integer idTutor);

    @Query("SELECT d FROM Direccion d WHERE d.colonia LIKE %:colonia% AND d.estado = :estado")
    List<Direccion> findByColoniaAndEstado(@Param("colonia") String colonia, @Param("estado") String estado);

    List<Direccion> findByEstatus(Boolean estatus);
}