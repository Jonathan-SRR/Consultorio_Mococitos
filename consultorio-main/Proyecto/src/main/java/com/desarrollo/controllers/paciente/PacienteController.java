package com.desarrollo.controllers.paciente;

import com.desarrollo.entities.pacientes.Tutor;
import com.desarrollo.service.paciente.PacienteService;
import com.desarrollo.service.paciente.TutorService;
import com.desarrollo.service.paciente.DireccionService;
import com.desarrollo.repositories.paciente.PacienteRepository;
import com.desarrollo.repositories.paciente.TutorRepository;
import com.desarrollo.repositories.paciente.DireccionRepository;
import com.desarrollo.types.paciente.PacienteType;
import com.desarrollo.types.paciente.TutorType;
import com.desarrollo.types.paciente.DireccionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8900")
public class PacienteController {

    private final PacienteService pacienteService;
    private final TutorService tutorService;
    private final DireccionService direccionService;
    private final PacienteRepository pacienteRepository;
    private final TutorRepository tutorRepository;
    private final DireccionRepository direccionRepository;

    @GetMapping
    public ResponseEntity<List<PacienteType>> obtenerTodos() {
        return ResponseEntity.ok(pacienteService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteType> obtenerPorId(@PathVariable Integer id) {
        PacienteType paciente = pacienteService.obtenerPorId(id);
        return paciente != null ? ResponseEntity.ok(paciente) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crearPacienteSimple(@RequestBody Map<String, Object> request) {
        try {
            System.out.println("=== CREAR PACIENTE SIMPLE ===");
            System.out.println("Datos recibidos: " + request);

            // Validar campos requeridos del paciente
            if (!request.containsKey("nombre_paciente") ||
                    !request.containsKey("apellido_paterno") ||
                    !request.containsKey("fecha_nacimiento") ||
                    !request.containsKey("sexo")) {

                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Faltan campos requeridos: nombre_paciente, apellido_paterno, fecha_nacimiento, sexo");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Extraer datos del paciente del request
            PacienteType pacienteType = new PacienteType();
            pacienteType.setNombre_paciente(request.get("nombre_paciente").toString());
            pacienteType.setApellido_paterno(request.get("apellido_paterno").toString());

            if (request.containsKey("apellido_materno")) {
                pacienteType.setApellido_materno(request.get("apellido_materno").toString());
            } else {
                pacienteType.setApellido_materno("");
            }

            // Manejar fecha de nacimiento
            Object fechaNacimiento = request.get("fecha_nacimiento");
            try {
                pacienteType.setFecha_nacimiento(java.time.LocalDate.parse(fechaNacimiento.toString()));
            } catch (Exception e) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Formato de fecha inválido. Use YYYY-MM-DD");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            pacienteType.setSexo(request.get("sexo").toString());
            pacienteType.setEstatus(true);

            // Guardar paciente
            PacienteType pacienteGuardado = pacienteService.guardarPaciente(pacienteType);
            System.out.println("Paciente guardado con ID: " + pacienteGuardado.getId_paciente());

            Map<String, Object> response = new HashMap<>();
            response.put("id_paciente", pacienteGuardado.getId_paciente());
            response.put("message", "Paciente creado correctamente");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error al crear paciente: " + e.getMessage());
            e.printStackTrace();

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error interno al crear paciente: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteType> actualizar(@PathVariable Integer id, @RequestBody PacienteType pacienteType) {
        try {
            pacienteType.setId_paciente(id);
            PacienteType pacienteActualizado = pacienteService.guardarPaciente(pacienteType);
            return ResponseEntity.ok(pacienteActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<PacienteType>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(pacienteService.buscarPorNombre(nombre));
    }

    @GetMapping("/activos")
    public ResponseEntity<List<PacienteType>> obtenerActivos() {
        return ResponseEntity.ok(pacienteService.obtenerActivos());
    }

    // Endpoints para Tutores
    @GetMapping("/{idPaciente}/tutores")
    public ResponseEntity<List<TutorType>> obtenerTutoresPorPaciente(@PathVariable Integer idPaciente) {
        try {
            return ResponseEntity.ok(tutorService.obtenerPorPaciente(idPaciente));
        } catch (Exception e) {
            return ResponseEntity.ok(List.of());
        }
    }

    @PostMapping("/{idPaciente}/tutores")
    public ResponseEntity<?> crearTutor(@PathVariable Integer idPaciente, @RequestBody Map<String, Object> request) {
        try {
            System.out.println("=== CREAR TUTOR ===");
            System.out.println("ID Paciente: " + idPaciente);
            System.out.println("Datos tutor recibidos: " + request);

            // Validar que el paciente existe
            Optional<com.desarrollo.entities.pacientes.Pacientes> pacienteOpt = pacienteRepository.buscarPorId(idPaciente);
            if (pacienteOpt.isEmpty()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Paciente no encontrado con ID: " + idPaciente);
                System.out.println("Error: " + errorResponse);
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Validar campos requeridos del tutor
            if (!request.containsKey("nombre_tutor") ||
                    !request.containsKey("apellido_paterno") ||
                    !request.containsKey("correo") ||
                    !request.containsKey("telefono")) {

                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Faltan campos requeridos del tutor: nombre_tutor, apellido_paterno, correo, telefono");
                System.out.println("Error de validación: " + errorResponse);
                return ResponseEntity.badRequest().body(errorResponse);
            }

            TutorType tutorType = new TutorType();
            tutorType.setNombre_tutor(request.get("nombre_tutor").toString());
            tutorType.setApellido_paterno(request.get("apellido_paterno").toString());

            if (request.containsKey("apellido_materno")) {
                tutorType.setApellido_materno(request.get("apellido_materno").toString());
            } else {
                tutorType.setApellido_materno("");
            }

            tutorType.setCorreo(request.get("correo").toString());
            tutorType.setTelefono(request.get("telefono").toString());
            tutorType.setEstatus(true);
            tutorType.setId_paciente(idPaciente); // Usar el ID del path variable

            System.out.println("TutorType creado: " + tutorType);

            TutorType tutorGuardado = tutorService.guardarTutor(tutorType);
            System.out.println("Tutor guardado exitosamente con ID: " + tutorGuardado.getId_tutor());

            Map<String, Object> response = new HashMap<>();
            response.put("id_tutor", tutorGuardado.getId_tutor());
            response.put("message", "Tutor creado correctamente");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error al crear tutor: " + e.getMessage());
            e.printStackTrace();

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error interno al crear tutor: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    // Endpoints para Direcciones
    @GetMapping("/tutores/{idTutor}/direcciones")
    public ResponseEntity<List<DireccionType>> obtenerDireccionesPorTutor(@PathVariable Integer idTutor) {
        try {
            return ResponseEntity.ok(direccionService.obtenerPorTutor(idTutor));
        } catch (Exception e) {
            return ResponseEntity.ok(List.of());
        }
    }

    @PostMapping("/tutores/{idTutor}/direcciones")
    public ResponseEntity<?> crearDireccion(@PathVariable Integer idTutor, @RequestBody Map<String, Object> request) {
        try {
            System.out.println("=== CREAR DIRECCIÓN ===");
            System.out.println("ID Tutor: " + idTutor);
            System.out.println("Datos dirección recibidos: " + request);

            // Validar que el tutor existe
            Optional<Tutor> tutorOpt = tutorRepository.buscarPorId(idTutor);
            if (tutorOpt.isEmpty()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Tutor no encontrado con ID: " + idTutor);
                System.out.println("Error: " + errorResponse);
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Validar campos requeridos de la dirección
            if (!request.containsKey("calle") ||
                    !request.containsKey("colonia") ||
                    !request.containsKey("estado") ||
                    !request.containsKey("codigo_postal")) {

                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Faltan campos requeridos de la dirección: calle, colonia, estado, codigo_postal");
                System.out.println("Error de validación: " + errorResponse);
                return ResponseEntity.badRequest().body(errorResponse);
            }

            DireccionType direccionType = new DireccionType();
            direccionType.setCalle(request.get("calle").toString());
            direccionType.setColonia(request.get("colonia").toString());
            direccionType.setEstado(request.get("estado").toString());

            // Manejar número exterior
            if (request.containsKey("numero_ext")) {
                Object numeroExt = request.get("numero_ext");
                try {
                    if (numeroExt instanceof Integer) {
                        direccionType.setNumero_ext((Integer) numeroExt);
                    } else {
                        direccionType.setNumero_ext(Integer.parseInt(numeroExt.toString()));
                    }
                } catch (NumberFormatException e) {
                    direccionType.setNumero_ext(0);
                }
            } else {
                direccionType.setNumero_ext(0);
            }

            // Manejar código postal
            Object codigoPostal = request.get("codigo_postal");
            try {
                if (codigoPostal instanceof Integer) {
                    direccionType.setCodigo_postal((Integer) codigoPostal);
                } else {
                    direccionType.setCodigo_postal(Integer.parseInt(codigoPostal.toString()));
                }
            } catch (NumberFormatException e) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Código postal inválido: " + codigoPostal);
                return ResponseEntity.badRequest().body(errorResponse);
            }

            direccionType.setEstatus(true);
            direccionType.setId_tutor(idTutor); // Usar el ID del path variable

            System.out.println("DireccionType creado: " + direccionType);

            DireccionType direccionGuardada = direccionService.guardarDireccion(direccionType);
            System.out.println("Dirección guardada exitosamente con ID: " + direccionGuardada.getId_direccion());

            Map<String, Object> response = new HashMap<>();
            response.put("id_direccion", direccionGuardada.getId_direccion());
            response.put("message", "Dirección creada correctamente");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("ERROR al crear dirección: " + e.getMessage());
            e.printStackTrace();

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error interno al crear dirección: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}