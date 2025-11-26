package com.desarrollo.service.citas;

import com.desarrollo.entities.citas.Citas;
import com.desarrollo.repositories.citas.jpa.CitaJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaJpa citaJpa;

    public List<Citas> obtenerTodas() {
        return citaJpa.findAll();
    }

    public List<Citas> obtenerHoy() {
        String hoy = LocalDate.now().toString();
        return citaJpa.findAll().stream()
                .filter(c -> c.getFecha() != null && c.getFecha().startsWith(hoy))
                .toList();
    }
}
