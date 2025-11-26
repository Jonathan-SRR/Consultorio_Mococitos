package com.desarrollo.service.citas;

import com.desarrollo.entities.citas.Citas;
import com.desarrollo.entities.pacientes.Pacientes;
import com.desarrollo.repositories.citas.CitasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitasRepository citasRepository;

    public List<Citas> obtenerTodas() {
        return citasRepository.findAll();
    }

    public Optional<Citas> obtenerPorId(Integer id) {
        return citasRepository.findById(id);
    }

    public Citas guardar(Citas cita) {
        return citasRepository.save(cita);
    }

    public void eliminar(Integer id) {
        citasRepository.delete(id);
    }

    public List<Citas> obtenerPorPaciente(Integer idPaciente) {
        Pacientes p = new Pacientes();
        p.setIdPaciente(idPaciente);
        return citasRepository.findByPaciente(p);
    }

    public List<Citas> obtenerPorFechaAntes(LocalDateTime fecha) {
        return citasRepository.findByFechaBefore(fecha);
    }

    public List<Citas> obtenerPorFechaDespues(LocalDateTime fecha) {
        return citasRepository.findByFechaAfter(fecha);
    }

    public List<Citas> obtenerPorTipo(String tipo) {
        return citasRepository.findByTipoCitaContaining(tipo);
    }

    public List<Citas> obtenerPorEstatus(Integer estatus) {
        return citasRepository.findByEstatus(estatus);
    }

    /**
     * Obtiene solamente las citas de HOY
     */
    public List<Citas> obtenerHoy() {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicio = hoy.atStartOfDay();
        LocalDateTime fin = hoy.atTime(23, 59, 59);

        return citasRepository.findByFechaAfter(inicio)
                .stream()
                .filter(c -> c.getFecha() != null && c.getFecha().isBefore(fin))
                .toList();
    }
}




