package com.desarrollo.repositories.citas;

import com.desarrollo.entities.citas.Citas;
import com.desarrollo.entities.pacientes.Pacientes;
import com.desarrollo.repositories.citas.jpa.CitaJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CitasRepository {

    private final CitaJpa citaJpa;

    public List<Citas> findAll() {
        return citaJpa.findAll();
    }

    public Optional<Citas> findById(Integer id) {
        return citaJpa.findById(id);
    }

    public Citas save(Citas cita) {
        return citaJpa.save(cita);
    }

    public void delete(Integer id) {
        citaJpa.deleteById(id);
    }

    public List<Citas> findByFechaBefore(LocalDateTime fecha) {
        return citaJpa.findByFechaBefore(fecha);
    }

    public List<Citas> findByFechaAfter(LocalDateTime fecha) {
        return citaJpa.findByFechaAfter(fecha);
    }

    public List<Citas> findByTipoCitaContaining(String tipoCita) {
        return citaJpa.findByTipoCitaContaining(tipoCita);
    }

    public List<Citas> findByEstatus(Integer estatus) {
        return citaJpa.findByEstatus(estatus);
    }

    public Optional<Citas> findByIdCita(Integer idCita) {
        return citaJpa.findByIdCita(idCita);
    }

    public List<Citas> findByPaciente(Pacientes paciente) {
        return citaJpa.findByPaciente(paciente);
    }
}

