package com.desarrollo.service.paciente;

import com.desarrollo.entities.pacientes.Pacientes;
import com.desarrollo.entities.pacientes.Tutor;
import com.desarrollo.repositories.paciente.PacienteRepository;
import com.desarrollo.repositories.paciente.TutorRepository;
import com.desarrollo.types.paciente.TutorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TutorService {

    private final TutorRepository tutorRepository;
    private final PacienteRepository pacienteRepository;

    public TutorType guardarTutor(TutorType tutorType) {
        try {
            System.out.println("=== GUARDAR TUTOR - INICIO ===");
            System.out.println("TutorType recibido: " + tutorType);
            System.out.println("ID Paciente en TutorType: " + tutorType.getId_paciente());

            // Verificar que el paciente existe
            if (tutorType.getId_paciente() == null) {
                throw new IllegalArgumentException("ID del paciente es requerido");
            }

            System.out.println("Buscando paciente con ID: " + tutorType.getId_paciente());
            Optional<Pacientes> pacienteOpt = pacienteRepository.buscarPorId(tutorType.getId_paciente());

            if (pacienteOpt.isEmpty()) {
                System.out.println("ERROR: Paciente no encontrado con ID: " + tutorType.getId_paciente());
                throw new IllegalArgumentException("Paciente no encontrado con ID: " + tutorType.getId_paciente());
            }

            Pacientes paciente = pacienteOpt.get();
            System.out.println("Paciente encontrado: ID=" + paciente.getIdPaciente() + ", Nombre=" + paciente.getNombrePaciente());

            System.out.println("Convirtiendo TutorType a Tutor entity...");
            Tutor tutor = convertirTypeAEntity(tutorType);
            System.out.println("Tutor después de conversión (antes de setPaciente): " + tutor);

            tutor.setPaciente(paciente); // Establecer la relación
            System.out.println("Tutor después de setPaciente: " + tutor);
            System.out.println("Paciente en tutor: " + (tutor.getPaciente() != null ?
                    "ID=" + tutor.getPaciente().getIdPaciente() + ", Nombre=" + tutor.getPaciente().getNombrePaciente() : "NULL"));

            System.out.println("Guardando tutor en repository...");
            Tutor guardado = tutorRepository.guardar(tutor);
            System.out.println("Tutor guardado exitosamente: " + guardado);

            TutorType result = convertirEntityAType(guardado);
            System.out.println("TutorType resultado: " + result);
            System.out.println("=== GUARDAR TUTOR - EXITO ===");

            return result;
        } catch (Exception e) {
            System.err.println("=== GUARDAR TUTOR - ERROR ===");
            System.err.println("ERROR en TutorService.guardarTutor: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al guardar tutor: " + e.getMessage(), e);
        }
    }

    public List<TutorType> obtenerPorPaciente(Integer idPaciente) {
        try {
            System.out.println("=== OBTENER TUTORES POR PACIENTE ===");
            System.out.println("Buscando tutores para paciente ID: " + idPaciente);

            List<Tutor> tutores = tutorRepository.listarPorPaciente(idPaciente);
            System.out.println("Tutores encontrados: " + tutores.size());

            List<TutorType> resultado = tutores.stream()
                    .map(this::convertirEntityAType)
                    .collect(Collectors.toList());

            System.out.println("TutorTypes convertidos: " + resultado.size());
            return resultado;
        } catch (Exception e) {
            System.err.println("ERROR en TutorService.obtenerPorPaciente: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al obtener tutores por paciente: " + e.getMessage(), e);
        }
    }

    public List<TutorType> obtenerTodos() {
        try {
            System.out.println("=== OBTENER TODOS LOS TUTORES ===");
            List<Tutor> tutores = tutorRepository.listar();
            System.out.println("Total de tutores encontrados: " + tutores.size());

            return tutores.stream()
                    .map(this::convertirEntityAType)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("ERROR en TutorService.obtenerTodos: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al obtener todos los tutores: " + e.getMessage(), e);
        }
    }

    public void eliminarTutor(Integer id) {
        try {
            System.out.println("=== ELIMINAR TUTOR ===");
            System.out.println("Eliminando tutor con ID: " + id);
            tutorRepository.eliminar(id);
            System.out.println("Tutor eliminado exitosamente: ID=" + id);
        } catch (Exception e) {
            System.err.println("ERROR en TutorService.eliminarTutor: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar tutor: " + e.getMessage(), e);
        }
    }

    private Tutor convertirTypeAEntity(TutorType type) {
        Tutor entity = new Tutor();
        entity.setIdTutor(type.getId_tutor());
        entity.setNombreTutor(type.getNombre_tutor());
        entity.setApellidoPaterno(type.getApellido_paterno());
        entity.setApellidoMaterno(type.getApellido_materno());
        entity.setCorreo(type.getCorreo());
        entity.setTelefono(type.getTelefono());
        entity.setEstatus(type.getEstatus());
        // La relación con Paciente se establece en el método guardarTutor
        return entity;
    }

    private TutorType convertirEntityAType(Tutor entity) {
        TutorType type = new TutorType();
        type.setId_tutor(entity.getIdTutor());
        type.setNombre_tutor(entity.getNombreTutor());
        type.setApellido_paterno(entity.getApellidoPaterno());
        type.setApellido_materno(entity.getApellidoMaterno());
        type.setCorreo(entity.getCorreo());
        type.setTelefono(entity.getTelefono());
        type.setEstatus(entity.getEstatus());
        if (entity.getPaciente() != null) {
            type.setId_paciente(entity.getPaciente().getIdPaciente());
        }
        return type;
    }
}