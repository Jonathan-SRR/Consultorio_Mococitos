package com.desarrollo.repositories.paciente.jpa;

import com.desarrollo.entities.pacientes.Pacientes;
import com.desarrollo.repositories.DesarrolloJpa;
import java.util.List;

public interface PacienteJpa extends DesarrolloJpa<Pacientes, Integer> {

    // Buscar por coincidencia parcial en nombre o apellidos
    List<Pacientes> findByNombrePacienteContainingIgnoreCaseOrApellidoPaternoContainingIgnoreCaseOrApellidoMaternoContainingIgnoreCase(
            String nombre_paciente, String apellido_paterno, String apellido_materno
    );

    // Buscar por coincidencia con nombre completo
    List<Pacientes> findByNombrePacienteContainingIgnoreCase(String nombre);

}
