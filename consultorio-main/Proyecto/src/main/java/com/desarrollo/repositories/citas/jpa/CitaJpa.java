package com.desarrollo.repositories.citas.jpa;

import com.desarrollo.entities.citas.Citas;
import com.desarrollo.repositories.DesarrolloJpa;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaJpa extends DesarrolloJpa<Citas, Integer> {

}

