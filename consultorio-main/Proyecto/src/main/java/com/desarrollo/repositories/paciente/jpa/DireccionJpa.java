package com.desarrollo.repositories.paciente.jpa;

import com.desarrollo.entities.pacientes.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DireccionJpa extends JpaRepository<Direccion, Integer> {

    List<Direccion> findByTutor_IdTutor(Integer idTutor);
}
