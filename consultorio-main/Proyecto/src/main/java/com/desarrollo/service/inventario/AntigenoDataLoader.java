/*  Esta clase es una prueba para la conexion (BD)Antigenos - AntigenosRepository

package com.desarrollo.service.inventario;

import com.desarrollo.entities.administracion.Antigeno;
import com.desarrollo.repositories.administracion.AntigenoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AntigenoDataLoader implements CommandLineRunner {

    @Autowired
    private AntigenoRepository antigenoRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== INICIANDO PRUEBA DEL REPOSITORY ===");

        try {
            // 1. PRUEBA DE GUARDAR
            System.out.println("1. Probando guardar...");
            Antigeno nuevo = new Antigeno();
            nuevo.setNombreAntigeno("Antígeno Test Repository");
            nuevo.setFechaCaducidad(LocalDate.now().plusYears(1));
            nuevo.setCategoria("TEST");
            nuevo.setCantidad(100.0);
            nuevo.setLote("L-REPO-001");
            nuevo.setPermiso("SSA-REPO-001");

            Antigeno guardado = antigenoRepository.guardar(nuevo);
            System.out.println(" GUARDADO - ID: " + guardado.getId());

            // 2. PRUEBA DE BUSCAR TODOS
            System.out.println("2. Probando buscar todos...");
            var todos = antigenoRepository.buscarTodos();
            System.out.println(" ENCONTRADOS: " + todos.size() + " antígenos");

            // 3. PRUEBA DE BUSCAR POR ID
            System.out.println("3. Probando buscar por ID...");
            var porId = antigenoRepository.buscarPorId(guardado.getId());
            if (porId.isPresent()) {
                System.out.println(" POR ID - " + porId.get().getNombreAntigeno());
            }

            // 4. PRUEBA DE BUSCAR POR CATEGORÍA
            System.out.println("4. Probando buscar por categoría...");
            var porCategoria = antigenoRepository.buscarPorCategoria("TEST");
            System.out.println(" POR CATEGORÍA: " + porCategoria.size());

            System.out.println(" REPOSITORY FUNCIONANDO CORRECTAMENTE!");

        } catch (Exception e) {
            System.err.println(" ERROR EN REPOSITORY: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
        */