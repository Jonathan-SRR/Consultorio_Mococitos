package com.desarrollo.controllers.catalogos;

import com.desarrollo.service.catalogos.AntigenoService;
import com.desarrollo.types.catalogos.AntigenoType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author anny.cont
 */
@RestController
@RequestMapping("/api/antigenos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8900")
public class AntigenoController {

    private final AntigenoService antigenoService;

    @GetMapping
    public ResponseEntity<List<AntigenoType>> obtenerTodos() {
        return ResponseEntity.ok(antigenoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AntigenoType> obtenerPorId(@PathVariable Integer id) {
        AntigenoType antigeno = antigenoService.obtenerPorId(id);
        return antigeno != null ? ResponseEntity.ok(antigeno) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<AntigenoType> crear(@RequestBody AntigenoType antigenoType) {
        return ResponseEntity.ok(antigenoService.guardarAntigeno(antigenoType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AntigenoType> actualizar(@PathVariable Integer id, @RequestBody AntigenoType antigenoType) {
        antigenoType.setId(id);
        return ResponseEntity.ok(antigenoService.guardarAntigeno(antigenoType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        antigenoService.eliminarAntigeno(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/proximos-caducar")
    public ResponseEntity<List<AntigenoType>> obtenerProximosACaducar() {
        return ResponseEntity.ok(antigenoService.obtenerProximosACaducar());
    }

    @GetMapping("/caducados")
    public ResponseEntity<List<AntigenoType>> obtenerCaducados() {
        return ResponseEntity.ok(antigenoService.obtenerCaducados());
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<AntigenoType>> obtenerPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(antigenoService.obtenerPorCategoria(categoria));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<AntigenoType>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(antigenoService.obtenerPorNombre(nombre));
    }
}
