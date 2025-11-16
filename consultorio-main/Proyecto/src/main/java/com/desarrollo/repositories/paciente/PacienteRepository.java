package com.desarrollo.repositories.paciente;

import com.desarrollo.entities.pacientes.Pacientes;
import com.desarrollo.repositories.paciente.jpa.PacienteJpa;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
@RequiredArgsConstructor
public class PacienteRepository {

    private final PacienteJpa pacienteJpa;

    public Pacientes guardar(Pacientes paciente){return pacienteJpa.save(paciente);}

    public Pacientes actualizar(Pacientes paciente) { return pacienteJpa.save(paciente); }

    public List<Pacientes> listar() { return pacienteJpa.findAll(); }

    public List<Pacientes> buscarPacientePorNombre(
            String nombre,
            String apellidoPaterno,
            String apellidoMaterno
    ) {
        return pacienteJpa
                .findByNombrePacienteContainingIgnoreCaseOrApellidoPaternoContainingIgnoreCaseOrApellidoMaternoContainingIgnoreCase(
                        nombre,
                        apellidoPaterno,
                        apellidoMaterno
                );
    }

    public void eliminarPaciente(Integer id) { pacienteJpa.deleteById(id); }





}
