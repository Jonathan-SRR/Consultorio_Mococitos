package com.desarrollo.repositories.paciente.jpa;

import com.desarrollo.entities.pacientes.Tutor;
import com.desarrollo.repositories.DesarrolloJpa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TutorJpa extends DesarrolloJpa<Tutor, Integer> {

    List<Tutor> findByNombreTutorContaining(String nombre);

    Optional<Tutor> findByCorreo(String correo);

    List<Tutor> findByTelefono(String telefono);

    List<Tutor> findByPacienteIdPaciente(Integer idPaciente);

    @Query("SELECT t FROM Tutor t WHERE t.nombreTutor LIKE %:nombre% OR t.apellidoPaterno LIKE %:apellido%")
    List<Tutor> findByNombreOrApellido(@Param("nombre") String nombre, @Param("apellido") String apellido);

    List<Tutor> findByEstatus(Boolean estatus);
}