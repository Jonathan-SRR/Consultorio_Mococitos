package com.desarrollo.service.paciente;

import com.desarrollo.entities.pacientes.Direccion;
import com.desarrollo.entities.pacientes.Tutor;
import com.desarrollo.repositories.paciente.DireccionRepository;
import com.desarrollo.repositories.paciente.TutorRepository;
import com.desarrollo.types.paciente.DireccionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DireccionService {

    private final DireccionRepository direccionRepository;
    private final TutorRepository tutorRepository; // Agregar esta dependencia

    public DireccionType guardarDireccion(DireccionType direccionType) {
        try {
            System.out.println("=== GUARDAR DIRECCIÓN - INICIO ===");
            System.out.println("DireccionType recibido: " + direccionType);
            System.out.println("ID Tutor en DireccionType: " + direccionType.getId_tutor());

            // Verificar que el tutor existe
            if (direccionType.getId_tutor() == null) {
                throw new IllegalArgumentException("ID del tutor es requerido");
            }

            System.out.println("Buscando tutor con ID: " + direccionType.getId_tutor());
            Optional<Tutor> tutorOpt = tutorRepository.buscarPorId(direccionType.getId_tutor());

            if (tutorOpt.isEmpty()) {
                System.out.println("ERROR: Tutor no encontrado con ID: " + direccionType.getId_tutor());
                throw new IllegalArgumentException("Tutor no encontrado con ID: " + direccionType.getId_tutor());
            }

            Tutor tutor = tutorOpt.get();
            System.out.println("Tutor encontrado: ID=" + tutor.getIdTutor() + ", Nombre=" + tutor.getNombreTutor());

            System.out.println("Convirtiendo DireccionType a Direccion entity...");
            Direccion direccion = convertirTypeAEntity(direccionType);
            System.out.println("Dirección después de conversión (antes de setTutor): " + direccion);

            direccion.setTutor(tutor); // Establecer la relación
            System.out.println("Dirección después de setTutor: " + direccion);
            System.out.println("Tutor en dirección: " + (direccion.getTutor() != null ?
                    "ID=" + direccion.getTutor().getIdTutor() + ", Nombre=" + direccion.getTutor().getNombreTutor() : "NULL"));

            System.out.println("Guardando dirección en repository...");
            Direccion guardada = direccionRepository.guardar(direccion);
            System.out.println("Dirección guardada exitosamente: " + guardada);

            DireccionType result = convertirEntityAType(guardada);
            System.out.println("DireccionType resultado: " + result);
            System.out.println("=== GUARDAR DIRECCIÓN - EXITO ===");

            return result;
        } catch (Exception e) {
            System.err.println("=== GUARDAR DIRECCIÓN - ERROR ===");
            System.err.println("ERROR en DireccionService.guardarDireccion: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al guardar dirección: " + e.getMessage(), e);
        }
    }

    public List<DireccionType> obtenerPorTutor(Integer idTutor) {
        try {
            System.out.println("=== OBTENER DIRECCIONES POR TUTOR ===");
            System.out.println("Buscando direcciones para tutor ID: " + idTutor);

            List<Direccion> direcciones = direccionRepository.listarPorTutor(idTutor);
            System.out.println("Direcciones encontradas: " + direcciones.size());

            List<DireccionType> resultado = direcciones.stream()
                    .map(this::convertirEntityAType)
                    .collect(Collectors.toList());

            System.out.println("DireccionTypes convertidos: " + resultado.size());
            return resultado;
        } catch (Exception e) {
            System.err.println("ERROR en DireccionService.obtenerPorTutor: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al obtener direcciones por tutor: " + e.getMessage(), e);
        }
    }

    public List<DireccionType> obtenerTodos() {
        try {
            System.out.println("=== OBTENER TODAS LAS DIRECCIONES ===");
            List<Direccion> direcciones = direccionRepository.listar();
            System.out.println("Total de direcciones encontradas: " + direcciones.size());

            return direcciones.stream()
                    .map(this::convertirEntityAType)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("ERROR en DireccionService.obtenerTodos: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al obtener todas las direcciones: " + e.getMessage(), e);
        }
    }

    public void eliminarDireccion(Integer id) {
        try {
            System.out.println("=== ELIMINAR DIRECCIÓN ===");
            System.out.println("Eliminando dirección con ID: " + id);
            direccionRepository.eliminar(id);
            System.out.println("Dirección eliminada exitosamente: ID=" + id);
        } catch (Exception e) {
            System.err.println("ERROR en DireccionService.eliminarDireccion: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar dirección: " + e.getMessage(), e);
        }
    }

    private Direccion convertirTypeAEntity(DireccionType type) {
        Direccion entity = new Direccion();
        entity.setIdDireccion(type.getId_direccion());
        entity.setCalle(type.getCalle());
        entity.setNumeroExt(type.getNumero_ext());
        entity.setColonia(type.getColonia());
        entity.setEstado(type.getEstado());
        entity.setCodigoPostal(type.getCodigo_postal());
        entity.setEstatus(type.getEstatus());
        // La relación con Tutor se establece en el método guardarDireccion
        return entity;
    }

    private DireccionType convertirEntityAType(Direccion entity) {
        DireccionType type = new DireccionType();
        type.setId_direccion(entity.getIdDireccion());
        type.setCalle(entity.getCalle());
        type.setNumero_ext(entity.getNumeroExt());
        type.setColonia(entity.getColonia());
        type.setEstado(entity.getEstado());
        type.setCodigo_postal(entity.getCodigoPostal());
        type.setEstatus(entity.getEstatus());
        if (entity.getTutor() != null) {
            type.setId_tutor(entity.getTutor().getIdTutor());
        }
        return type;
    }
}