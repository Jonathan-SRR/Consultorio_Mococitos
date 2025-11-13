package com.desarrollo.service.inventario;

import com.desarrollo.entities.administracion.Antigeno;
import com.desarrollo.repositories.administracion.AntigenoRepository;
import com.desarrollo.types.AntigenoType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AntigenoService {

    private final AntigenoRepository antigenoRepository;

    public AntigenoType guardarAntigeno(AntigenoType antigenoType) {
        Optional<Antigeno> existente = antigenoRepository
                .buscarPorNombreExacto(antigenoType.getNombre_antigeno());

        if (existente.isPresent() &&
                (antigenoType.getId() == null || !existente.get().getId().equals(antigenoType.getId()))) {
            throw new IllegalArgumentException("Ya existe un antígeno con ese nombre.");
        }
        Antigeno antigeno = convertirTypeAEntity(antigenoType);
        Antigeno guardado = antigenoRepository.guardar(antigeno);
        return convertirEntityAType(guardado);
    }

    public List<AntigenoType> obtenerTodos() {
        return antigenoRepository.buscarTodos()
                .stream()
                .map(this::convertirEntityAType)
                .collect(Collectors.toList());
    }

    public AntigenoType obtenerPorId(Integer id) {
        return antigenoRepository.buscarPorId(id)
                .map(this::convertirEntityAType)
                .orElse(null);
    }

    public void eliminarAntigeno(Integer id) {
        antigenoRepository.eliminar(id);
    }

    public List<AntigenoType> obtenerProximosACaducar() {
        return antigenoRepository.buscarProximosACaducar()
                .stream()
                .map(this::convertirEntityAType)
                .collect(Collectors.toList());
    }

    public List<AntigenoType> obtenerCaducados() {
        return antigenoRepository.buscarCaducados()
                .stream()
                .map(this::convertirEntityAType)
                .collect(Collectors.toList());
    }

    public List<AntigenoType> obtenerPorCategoria(String categoria) {
        return antigenoRepository.buscarPorCategoria(categoria)
                .stream()
                .map(this::convertirEntityAType)
                .collect(Collectors.toList());
    }

    public List<AntigenoType> obtenerPorNombre(String nombre) {
        return antigenoRepository.buscarPorNombre(nombre)
                .stream()
                .map(this::convertirEntityAType)
                .collect(Collectors.toList());
    }

    private Antigeno convertirTypeAEntity(AntigenoType type) {
        Antigeno entity = new Antigeno();
        entity.setId(type.getId());
        entity.setNombreAntigeno(type.getNombre_antigeno());
        entity.setFechaCaducidad(type.getFecha_caducidad());
        entity.setCategoria(type.getCategoria());
        entity.setCantidad(type.getCantidad());
        entity.setLote(type.getLote());
        entity.setPermiso(type.getPermiso());
        return entity;
    }

    private AntigenoType convertirEntityAType(Antigeno entity) {
        AntigenoType type = new AntigenoType();
        type.setId(entity.getId());
        type.setNombre_antigeno(entity.getNombreAntigeno());
        type.setFecha_caducidad(entity.getFechaCaducidad());
        type.setCategoria(entity.getCategoria());
        type.setCantidad(entity.getCantidad());
        type.setLote(entity.getLote());
        type.setPermiso(entity.getPermiso());
        return type;
    }
}
