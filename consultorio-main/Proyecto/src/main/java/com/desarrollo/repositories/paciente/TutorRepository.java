package com.desarrollo.repositories.paciente;

import com.desarrollo.entities.pacientes.Tutor;
import com.desarrollo.repositories.paciente.jpa.TutorJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
@RequiredArgsConstructor
public class TutorRepository {

    private final TutorJpa tutorJpa;

    public Tutor guardar(Tutor tutor) {
        try {
            System.out.println("=== TUTOR REPOSITORY - GUARDAR ===");
            System.out.println("Tutor a guardar: Nombre=" + tutor.getNombreTutor() +
                    ", ID Paciente=" + (tutor.getPaciente() != null ? tutor.getPaciente().getIdPaciente() : "NULL"));
            Tutor resultado = tutorJpa.save(tutor);
            System.out.println("Tutor guardado exitosamente: ID=" + resultado.getIdTutor());
            return resultado;
        } catch (Exception e) {
            System.err.println("ERROR en TutorRepository.guardar: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Optional<Tutor> buscarPorId(Integer id) {
        try {
            System.out.println("=== TUTOR REPOSITORY - BUSCAR POR ID ===");
            System.out.println("Buscando tutor por ID: " + id);
            Optional<Tutor> resultado = tutorJpa.findById(id);

            if (resultado.isPresent()) {
                Tutor tutor = resultado.get();
                System.out.println("Tutor encontrado: ID=" + tutor.getIdTutor() +
                        ", Nombre=" + tutor.getNombreTutor());
            } else {
                System.out.println("Tutor NO encontrado con ID: " + id);
            }

            return resultado;
        } catch (Exception e) {
            System.err.println("ERROR en TutorRepository.buscarPorId: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public List<Tutor> listar() {
        try {
            System.out.println("=== TUTOR REPOSITORY - LISTAR ===");
            List<Tutor> resultado = tutorJpa.findAll();
            System.out.println("Total de tutores encontrados: " + resultado.size());
            return resultado;
        } catch (Exception e) {
            System.err.println("ERROR en TutorRepository.listar: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void eliminar(Integer id) {
        try {
            System.out.println("=== TUTOR REPOSITORY - ELIMINAR ===");
            System.out.println("Eliminando tutor con ID: " + id);
            tutorJpa.deleteById(id);
            System.out.println("Tutor eliminado exitosamente: ID=" + id);
        } catch (Exception e) {
            System.err.println("ERROR en TutorRepository.eliminar: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public List<Tutor> listarPorPaciente(Integer idPaciente) {
        try {
            System.out.println("=== TUTOR REPOSITORY - LISTAR POR PACIENTE ===");
            System.out.println("Buscando tutores para paciente ID: " + idPaciente);
            List<Tutor> resultado = tutorJpa.findByPacienteIdPaciente(idPaciente);
            System.out.println("Tutores encontrados: " + resultado.size());
            return resultado;
        } catch (Exception e) {
            System.err.println("ERROR en TutorRepository.listarPorPaciente: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Optional<Tutor> buscarPorCorreo(String correo) {
        try {
            System.out.println("=== TUTOR REPOSITORY - BUSCAR POR CORREO ===");
            System.out.println("Buscando tutor por correo: " + correo);
            Optional<Tutor> resultado = tutorJpa.findByCorreo(correo);

            if (resultado.isPresent()) {
                System.out.println("Tutor encontrado por correo: " + correo);
            } else {
                System.out.println("Tutor NO encontrado con correo: " + correo);
            }

            return resultado;
        } catch (Exception e) {
            System.err.println("ERROR en TutorRepository.buscarPorCorreo: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public List<Tutor> buscarPorNombre(String nombre) {
        try {
            System.out.println("=== TUTOR REPOSITORY - BUSCAR POR NOMBRE ===");
            System.out.println("Buscando tutor por nombre: " + nombre);
            List<Tutor> resultado = tutorJpa.findByNombreTutorContaining(nombre);
            System.out.println("Tutores encontrados: " + resultado.size());
            return resultado;
        } catch (Exception e) {
            System.err.println("ERROR en TutorRepository.buscarPorNombre: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}