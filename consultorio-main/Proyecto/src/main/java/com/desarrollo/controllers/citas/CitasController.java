package com.desarrollo.controllers.citas;

import com.desarrollo.entities.citas.Citas;
import com.desarrollo.service.citas.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8900")
public class CitasController {

    private final CitaService citasService;

    @GetMapping("/calendar")
    public List<Citas> getAll() {
        return citasService.obtenerTodas();
    }

    @GetMapping("/hoy")
    public List<Citas> getHoy() {
        return citasService.obtenerHoy();
    }
}

