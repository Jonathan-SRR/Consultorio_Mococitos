package com.desarrollo.repositories.paciente.jpa;

import com.desarrollo.entities.pacientes.Pacientes;
import com.desarrollo.repositories.DesarrolloJpa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PacienteJpa extends DesarrolloJpa<Pacientes, Integer> {

    List<Pacientes> findByNombrePacienteContaining(String nombre);

    List<Pacientes> findByApellidoPaternoContaining(String apellidoPaterno);

    List<Pacientes> findByApellidoMaternoContaining(String apellidoMaterno);

    @Query("SELECT p FROM Pacientes p WHERE " +
            "p.nombrePaciente LIKE %:nombre% OR " +
            "p.apellidoPaterno LIKE %:apellidoPaterno% OR " +
            "p.apellidoMaterno LIKE %:apellidoMaterno%")
    List<Pacientes> findByNombrePacienteContainingOrApellidoPaternoContainingOrApellidoMaternoContaining(
            @Param("nombre") String nombre,
            @Param("apellidoPaterno") String apellidoPaterno,
            @Param("apellidoMaterno") String apellidoMaterno);

    Optional<Pacientes> findByNombrePacienteAndApellidoPaternoAndApellidoMaterno(
            String nombrePaciente, String apellidoPaterno, String apellidoMaterno);

    List<Pacientes> findByEstatus(Boolean estatus);
}