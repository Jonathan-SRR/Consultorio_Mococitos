package com.desarrollo.repositories.paciente;

import com.desarrollo.entities.pacientes.Tutor;
import com.desarrollo.repositories.paciente.jpa.TutorJpa;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class TutorRepository {

    private final TutorJpa tutorJpa;

    public List<Tutor> listar() { return tutorJpa.findAll(); }

    public Tutor guardar(Tutor t) { return tutorJpa.save(t);}

    public void eliminar(Integer id) { tutorJpa.deleteById(id); }

    public List<Tutor> listarPorPaciente(Integer idPaciente) { return tutorJpa.findByPaciente_IdPaciente(idPaciente); }
}
