package com.desarrollo.repositories.citas.jpa;

import com.desarrollo.entities.citas.Citas;
import com.desarrollo.entities.pacientes.Pacientes;
import com.desarrollo.repositories.DesarrolloJpa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CitaJpa extends DesarrolloJpa<Citas, Integer> {

    List<Citas> findByPaciente(Pacientes paciente);
    List<Citas> findByFechaBefore(LocalDateTime fecha);

    List<Citas> findByFechaAfter(LocalDateTime fecha);

    List<Citas> findByTipoCitaContaining(String tipoCita);

    List<Citas> findByEstatus(Integer estatus);

    Optional<Citas> findByIdCita(Integer idCita);
}

