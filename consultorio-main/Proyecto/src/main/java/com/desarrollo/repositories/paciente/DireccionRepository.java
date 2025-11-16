package com.desarrollo.repositories.paciente;

import com.desarrollo.entities.pacientes.Direccion;
import com.desarrollo.repositories.paciente.jpa.DireccionJpa;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class DireccionRepository {

    private final DireccionJpa direccionJpa;

    public List<Direccion> listar() { return direccionJpa.findAll(); }

    public Direccion guardar(Direccion d) { return direccionJpa.save(d); }

    public void eliminar(Integer id) { direccionJpa.deleteById(id);}

    public List<Direccion> listarPorTutor(Integer idTutor) { return direccionJpa.findByTutor_IdTutor(idTutor); }
}

