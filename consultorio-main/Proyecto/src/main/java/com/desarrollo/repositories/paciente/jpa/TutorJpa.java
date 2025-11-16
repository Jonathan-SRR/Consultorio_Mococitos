package com.desarrollo.repositories.paciente.jpa;

import com.desarrollo.entities.pacientes.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorJpa extends JpaRepository<Tutor, Integer> {

    List<Tutor> findByPaciente_IdPaciente(Integer idPaciente);
}

