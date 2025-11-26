package com.desarrollo.repositories.paciente;

import com.desarrollo.entities.pacientes.Direccion;
import com.desarrollo.repositories.paciente.jpa.DireccionJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
@RequiredArgsConstructor
public class DireccionRepository {

    private final DireccionJpa direccionJpa;

    public Direccion guardar(Direccion direccion) {
        return direccionJpa.save(direccion);
    }

    public Optional<Direccion> buscarPorId(Integer id) {
        return direccionJpa.findById(id);
    }

    public List<Direccion> listar() {
        return direccionJpa.findAll();
    }

    public void eliminar(Integer id) {
        direccionJpa.deleteById(id);
    }

    public List<Direccion> listarPorTutor(Integer idTutor) {
        return direccionJpa.findByTutorIdTutor(idTutor);
    }

    public List<Direccion> buscarPorColonia(String colonia) {
        return direccionJpa.findByColoniaContaining(colonia);
    }

    public List<Direccion> buscarPorEstado(String estado) {
        return direccionJpa.findByEstado(estado);
    }

    public List<Direccion> buscarPorCodigoPostal(Integer codigoPostal) {
        return direccionJpa.findByCodigoPostal(codigoPostal);
    }
}