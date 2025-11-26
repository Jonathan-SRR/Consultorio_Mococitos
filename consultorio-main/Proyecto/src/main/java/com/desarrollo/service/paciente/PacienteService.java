package com.desarrollo.service.paciente;

import com.desarrollo.entities.pacientes.Pacientes;
import com.desarrollo.repositories.paciente.PacienteRepository;
import com.desarrollo.types.paciente.PacienteType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteType guardarPaciente(PacienteType pacienteType) {
        // Validar nombre único si es necesario
        Optional<Pacientes> existente = pacienteRepository
                .buscarPorNombreCompleto(
                        pacienteType.getNombre_paciente(),
                        pacienteType.getApellido_paterno(),
                        pacienteType.getApellido_materno()
                );

        if (existente.isPresent() &&
                (pacienteType.getId_paciente() == null ||
                        !existente.get().getIdPaciente().equals(pacienteType.getId_paciente()))) {
            throw new IllegalArgumentException("Ya existe un paciente con ese nombre completo.");
        }

        Pacientes paciente = convertirTypeAEntity(pacienteType);
        Pacientes guardado = pacienteRepository.guardar(paciente);
        return convertirEntityAType(guardado);
    }

    public List<PacienteType> obtenerTodos() {
        try {
            System.out.println("=== OBTENER TODOS LOS PACIENTES ===");
            List<Pacientes> pacientes = pacienteRepository.listar();
            System.out.println("Total de pacientes encontrados: " + pacientes.size());

            return pacientes.stream()
                    .map(this::convertirEntityAType)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("ERROR en PacienteService.obtenerTodos: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al obtener todos los pacientes: " + e.getMessage(), e);
        }
    }

    public PacienteType obtenerPorId(Integer id) {
        try {
            System.out.println("=== OBTENER PACIENTE POR ID ===");
            System.out.println("Buscando paciente con ID: " + id);

            Optional<Pacientes> pacienteOpt = pacienteRepository.buscarPorId(id);
            if (pacienteOpt.isPresent()) {
                Pacientes paciente = pacienteOpt.get();
                System.out.println("Paciente encontrado: ID=" + paciente.getIdPaciente() + ", Nombre=" + paciente.getNombrePaciente());
                return convertirEntityAType(paciente);
            } else {
                System.out.println("Paciente NO encontrado con ID: " + id);
                return null;
            }
        } catch (Exception e) {
            System.err.println("ERROR en PacienteService.obtenerPorId: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al obtener paciente por ID: " + e.getMessage(), e);
        }
    }

    public void eliminarPaciente(Integer id) {
        try {
            System.out.println("=== ELIMINAR PACIENTE ===");
            System.out.println("Eliminando paciente con ID: " + id);
            pacienteRepository.eliminarPaciente(id);
            System.out.println("Paciente eliminado exitosamente: ID=" + id);
        } catch (Exception e) {
            System.err.println("ERROR en PacienteService.eliminarPaciente: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar paciente: " + e.getMessage(), e);
        }
    }

    public List<PacienteType> buscarPorNombre(String nombre) {
        try {
            System.out.println("=== BUSCAR PACIENTES POR NOMBRE ===");
            System.out.println("Buscando pacientes con nombre: " + nombre);

            List<Pacientes> pacientes = pacienteRepository.buscarPacientePorNombre(nombre, nombre, nombre);
            System.out.println("Pacientes encontrados: " + pacientes.size());

            return pacientes.stream()
                    .map(this::convertirEntityAType)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("ERROR en PacienteService.buscarPorNombre: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al buscar pacientes por nombre: " + e.getMessage(), e);
        }
    }

    public List<PacienteType> obtenerActivos() {
        try {
            System.out.println("=== OBTENER PACIENTES ACTIVOS ===");
            List<Pacientes> pacientes = pacienteRepository.listar();

            List<PacienteType> activos = pacientes.stream()
                    .filter(p -> p.getEstatus() != null && p.getEstatus())
                    .map(this::convertirEntityAType)
                    .collect(Collectors.toList());

            System.out.println("Pacientes activos encontrados: " + activos.size());
            return activos;
        } catch (Exception e) {
            System.err.println("ERROR en PacienteService.obtenerActivos: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al obtener pacientes activos: " + e.getMessage(), e);
        }
    }

    private Pacientes convertirTypeAEntity(PacienteType type) {
        Pacientes entity = new Pacientes();
        entity.setIdPaciente(type.getId_paciente());
        entity.setNombrePaciente(type.getNombre_paciente());
        entity.setApellidoPaterno(type.getApellido_paterno());
        entity.setApellidoMaterno(type.getApellido_materno());
        entity.setFechaNacimiento(type.getFecha_nacimiento());
        entity.setSexo(type.getSexo());
        entity.setEstatus(type.getEstatus());
        return entity;
    }

    private PacienteType convertirEntityAType(Pacientes entity) {
        PacienteType type = new PacienteType();
        type.setId_paciente(entity.getIdPaciente());
        type.setNombre_paciente(entity.getNombrePaciente());
        type.setApellido_paterno(entity.getApellidoPaterno());
        type.setApellido_materno(entity.getApellidoMaterno());
        type.setFecha_nacimiento(entity.getFechaNacimiento());
        type.setSexo(entity.getSexo());
        type.setEstatus(entity.getEstatus());
        return type;
    }
}