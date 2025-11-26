package com.desarrollo.controllers.citas;

import com.desarrollo.entities.citas.Citas;
import com.desarrollo.service.citas.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitasController {

    private final CitaService citaService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @GetMapping
    public List<Citas> obtenerTodas() {
        return citaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Citas obtenerPorId(@PathVariable Integer id) {
        return citaService.obtenerPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada"));
    }

    @PostMapping
    public Citas guardar(@RequestBody Citas cita) {
        return citaService.guardar(cita);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        citaService.eliminar(id);
    }

    // ---- Filtros ----

    @GetMapping("/paciente/{idPaciente}")
    public List<Citas> obtenerPorPaciente(@PathVariable Integer idPaciente) {
        return citaService.obtenerPorPaciente(idPaciente);
    }

    @GetMapping("/tipo/{tipo}")
    public List<Citas> obtenerPorTipo(@PathVariable String tipo) {
        return citaService.obtenerPorTipo(tipo);
    }

    @GetMapping("/estatus/{estatus}")
    public List<Citas> obtenerPorEstatus(@PathVariable Integer estatus) {
        return citaService.obtenerPorEstatus(estatus);
    }

    @GetMapping("/hoy")
    public List<Citas> obtenerHoy() {
        return citaService.obtenerHoy();
    }

    @PostMapping("/fecha/antes")
    public List<Citas> obtenerAntes(@RequestBody Map<String, String> body) {
        return citaService.obtenerPorFechaAntes(parseFecha(body.get("fecha")));
    }

    @PostMapping("/fecha/despues")
    public List<Citas> obtenerDespues(@RequestBody Map<String, String> body) {
        return citaService.obtenerPorFechaDespues(parseFecha(body.get("fecha")));
    }

    // ---- Helpers ----
    private LocalDateTime parseFecha(String fechaStr) {
        if (fechaStr == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo 'fecha' es obligatorio");

        try {
            return LocalDateTime.parse(fechaStr, FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Formato de fecha inválido. Usa ISO: 2025-11-25T10:30:00"
            );
        }
    }
}



