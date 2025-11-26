package com.desarrollo.entities.citas;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "citas")
public class Citas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCita;

    private Integer idPaciente;

    private String fecha;

    private String tipoCita;

    private String notas;

    private Integer estatus;
}
