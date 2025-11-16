/* package com.desarrollo.service.paciente;

//Esta clase es una prueba para la conexion
// (BD)Pacientes,tutor,direccion - respectivos Repository

import com.desarrollo.entities.pacientes.Pacientes;
import com.desarrollo.entities.pacientes.Tutor;
import com.desarrollo.entities.pacientes.Direccion;
import com.desarrollo.repositories.paciente.PacienteRepository;
import com.desarrollo.repositories.paciente.TutorRepository;
import com.desarrollo.repositories.paciente.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PacienteDataLoader implements CommandLineRunner {

    @Autowired
    private PacienteRepository pacientesRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("=== INICIANDO PRUEBA DE PACIENTE / TUTOR / DIRECCION ===");

        try {
            // ======================================================
            // 1. CREAR PACIENTE
            // ======================================================
            System.out.println("1. Probando guardar Paciente...");

            Pacientes paciente = new Pacientes();
            paciente.setNombrePaciente("Juan");
            paciente.setApellidoPaterno("Pérez");
            paciente.setApellidoMaterno("López");
            paciente.setFechaNacimiento(LocalDate.of(2010, 5, 20));
            paciente.setSexo("MASCULINO");
            paciente.setEstatus(true);

            Pacientes pacienteGuardado = pacientesRepository.guardar(paciente);
            System.out.println(" PACIENTE GUARDADO - ID: " + pacienteGuardado.getIdPaciente());

            // ======================================================
            // 2. CREAR TUTOR ASOCIADO AL PACIENTE
            // ======================================================
            System.out.println("2. Probando guardar Tutor...");

            Tutor tutor = new Tutor();
            tutor.setNombreTutor("María");
            tutor.setApellidoPaterno("González");
            tutor.setApellidoMaterno("Ramírez");
            tutor.setCorreo("maria@test.com");
            tutor.setTelefono("555-123-4567");
            tutor.setEstatus(true);
            tutor.setPaciente(pacienteGuardado);

            Tutor tutorGuardado = tutorRepository.guardar(tutor);
            System.out.println(" TUTOR GUARDADO - ID: " + tutorGuardado.getIdTutor());

            // ======================================================
            // 3. CREAR DIRECCIÓN ASOCIADA AL TUTOR
            // ======================================================
            System.out.println("3. Probando guardar Dirección...");

            Direccion direccion = new Direccion();
            direccion.setCalle("Av. Principal");
            direccion.setNumeroExt(100);
            direccion.setColonia("Centro");
            direccion.setEstado("México");
            direccion.setCodigoPostal(55000);
            direccion.setEstatus(true);
            direccion.setTutor(tutorGuardado);

            Direccion direccionGuardada = direccionRepository.guardar(direccion);
            System.out.println(" DIRECCION GUARDADA - ID: " + direccionGuardada.getIdDireccion());

            // ======================================================
            // 4. LISTAR PACIENTES
            // ======================================================
            System.out.println("4. Listando todos los pacientes...");
            var listaPacientes = pacientesRepository.listar();
            System.out.println(" TOTAL PACIENTES: " + listaPacientes.size());

            // ======================================================
            // 5. LISTAR TUTORES POR PACIENTE
            // ======================================================
            System.out.println("5. Listando tutores del paciente creado...");
            var tutores = tutorRepository.listarPorPaciente(pacienteGuardado.getIdPaciente());
            System.out.println(" TOTAL TUTORES DEL PACIENTE: " + tutores.size());

            // ======================================================
            // 6. LISTAR DIRECCIONES POR TUTOR
            // ======================================================
            System.out.println("6. Listando direcciones del tutor creado...");
            var direcciones = direccionRepository.listarPorTutor(tutorGuardado.getIdTutor());
            System.out.println(" TOTAL DIRECCIONES DEL TUTOR: " + direcciones.size());

            // ======================================================
            // 7. BUSCAR PACIENTE POR ID
            // ======================================================
            System.out.println("7. Probando buscar paciente por ID...");
            var encontrado = pacientesRepository.buscarPacientePorNombre(pacienteGuardado.getNombrePaciente(),
                    pacienteGuardado.getApellidoPaterno(),
                    pacienteGuardado.getApellidoMaterno());

            System.out.println(" BUSQUEDA ENCONTRÓ: " + encontrado.size() + " coincidencias");

            System.out.println(" === TODO FUNCIONANDO CORRECTAMENTE ===");

        } catch (Exception e) {
            System.err.println(" ERROR EN PRUEBA: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
 */