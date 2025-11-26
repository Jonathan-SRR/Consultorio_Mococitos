package com.desarrollo.repositories.paciente;

import com.desarrollo.entities.pacientes.Pacientes;
import com.desarrollo.repositories.paciente.jpa.PacienteJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
@RequiredArgsConstructor
public class PacienteRepository {

    private final PacienteJpa pacienteJpa;

    public Pacientes guardar(Pacientes paciente) {
        try {
            System.out.println("=== PACIENTE REPOSITORY - GUARDAR ===");
            System.out.println("Paciente a guardar: ID=" + paciente.getIdPaciente() + ", Nombre=" + paciente.getNombrePaciente());
            Pacientes resultado = pacienteJpa.save(paciente);
            System.out.println("Paciente guardado exitosamente: ID=" + resultado.getIdPaciente());
            return resultado;
        } catch (Exception e) {
            System.err.println("ERROR en PacienteRepository.guardar: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Pacientes actualizar(Pacientes paciente) {
        try {
            System.out.println("=== PACIENTE REPOSITORY - ACTUALIZAR ===");
            System.out.println("Paciente a actualizar: ID=" + paciente.getIdPaciente() + ", Nombre=" + paciente.getNombrePaciente());
            Pacientes resultado = pacienteJpa.save(paciente);
            System.out.println("Paciente actualizado exitosamente: ID=" + resultado.getIdPaciente());
            return resultado;
        } catch (Exception e) {
            System.err.println("ERROR en PacienteRepository.actualizar: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Optional<Pacientes> buscarPorId(Integer id) {
        try {
            System.out.println("=== PACIENTE REPOSITORY - BUSCAR POR ID ===");
            System.out.println("Buscando paciente por ID: " + id);
            Optional<Pacientes> resultado = pacienteJpa.findById(id);

            if (resultado.isPresent()) {
                Pacientes paciente = resultado.get();
                System.out.println("Paciente encontrado: ID=" + paciente.getIdPaciente() +
                        ", Nombre=" + paciente.getNombrePaciente() +
                        " " + paciente.getApellidoPaterno() +
                        " " + paciente.getApellidoMaterno());
            } else {
                System.out.println("Paciente NO encontrado con ID: " + id);
            }

            return resultado;
        } catch (Exception e) {
            System.err.println("ERROR en PacienteRepository.buscarPorId: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public List<Pacientes> listar() {
        try {
            System.out.println("=== PACIENTE REPOSITORY - LISTAR ===");
            List<Pacientes> resultado = pacienteJpa.findAll();
            System.out.println("Total de pacientes encontrados: " + resultado.size());
            return resultado;
        } catch (Exception e) {
            System.err.println("ERROR en PacienteRepository.listar: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void eliminarPaciente(Integer id) {
        try {
            System.out.println("=== PACIENTE REPOSITORY - ELIMINAR ===");
            System.out.println("Eliminando paciente con ID: " + id);
            pacienteJpa.deleteById(id);
            System.out.println("Paciente eliminado exitosamente: ID=" + id);
        } catch (Exception e) {
            System.err.println("ERROR en PacienteRepository.eliminarPaciente: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public List<Pacientes> buscarPacientePorNombre(String nombre, String apellidoPaterno, String apellidoMaterno) {
        try {
            System.out.println("=== PACIENTE REPOSITORY - BUSCAR POR NOMBRE ===");
            System.out.println("Buscando paciente por: nombre=" + nombre +
                    ", apellidoPaterno=" + apellidoPaterno +
                    ", apellidoMaterno=" + apellidoMaterno);

            List<Pacientes> resultado = pacienteJpa.findByNombrePacienteContainingOrApellidoPaternoContainingOrApellidoMaternoContaining(
                    nombre, apellidoPaterno, apellidoMaterno);

            System.out.println("Pacientes encontrados: " + resultado.size());
            return resultado;
        } catch (Exception e) {
            System.err.println("ERROR en PacienteRepository.buscarPacientePorNombre: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Optional<Pacientes> buscarPorNombreCompleto(String nombre, String apellidoPaterno, String apellidoMaterno) {
        try {
            System.out.println("=== PACIENTE REPOSITORY - BUSCAR POR NOMBRE COMPLETO ===");
            System.out.println("Buscando paciente por nombre completo: " + nombre + " " + apellidoPaterno + " " + apellidoMaterno);

            Optional<Pacientes> resultado = pacienteJpa.findByNombrePacienteAndApellidoPaternoAndApellidoMaterno(
                    nombre, apellidoPaterno, apellidoMaterno);

            if (resultado.isPresent()) {
                Pacientes paciente = resultado.get();
                System.out.println("Paciente encontrado: ID=" + paciente.getIdPaciente());
            } else {
                System.out.println("Paciente NO encontrado con nombre completo: " + nombre + " " + apellidoPaterno + " " + apellidoMaterno);
            }

            return resultado;
        } catch (Exception e) {
            System.err.println("ERROR en PacienteRepository.buscarPorNombreCompleto: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}