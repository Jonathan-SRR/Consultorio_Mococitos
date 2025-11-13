package com.desarrollo.repositories.administracion;


import com.desarrollo.entities.administracion.Antigeno;
import com.desarrollo.repositories.administracion.jpa.AntigenoJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
@RequiredArgsConstructor
public class AntigenoRepository {

    private final AntigenoJpa antigenoJpa;

    public Antigeno guardar(Antigeno antigeno) {
        return antigenoJpa.save(antigeno);
    }

    public Optional<Antigeno> buscarPorId(Integer id) {
        return antigenoJpa.findById(id);
    }

    public List<Antigeno> buscarTodos() {
        return antigenoJpa.findAll();
    }

    public void eliminar(Integer id) {
        antigenoJpa.deleteById(id);
    }

    public List<Antigeno> buscarPorCategoria(String categoria) {
        return antigenoJpa.findByCategoria(categoria);
    }

    public List<Antigeno> buscarProximosACaducar() {
        LocalDate fechaLimite = LocalDate.now().plusMonths(3); // 3 meses antes
        return antigenoJpa.findByFechaCaducidadBefore(fechaLimite);
    }

    public List<Antigeno> buscarPorNombre(String nombre) {
        return antigenoJpa.findByNombreAntigenoContaining(nombre);
    }

    public List<Antigeno> buscarCaducados() {
        return antigenoJpa.findByFechaCaducidadBefore(LocalDate.now());
    }

    public List<Antigeno> buscarPorLote(String lote) {
        return antigenoJpa.findByLote(lote);
    }

    public Optional<Antigeno> buscarPorNombreExacto(String nombreAntigeno) {
        return antigenoJpa.findByNombreAntigeno(nombreAntigeno);
    }
}

