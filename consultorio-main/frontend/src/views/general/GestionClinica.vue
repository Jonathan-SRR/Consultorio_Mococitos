<template>
  <div class="gestion-h-clinica-root">
    <div class="main-content">
      <main class="content">
        <section id="hclinic" style="margin-top: 20px;">
          <div class="hclinic-table-container table-responsive">
            <h2>Pacientes</h2>

            <form id="form-h-clinica" style="display: flex;">
              <div class="form-container">
                <div class="form-content">
                  <div class="parte1">

                    <!-- DATOS PACIENTE -->
                    <fieldset>
                      <legend>Datos del Paciente</legend>
                      <label>Nombre: <input type="text" name="nombre_paciente" required></label>
                      <label>Apellido Paterno: <input type="text" name="apellido_paterno_paciente" required></label>
                      <label>Apellido Materno: <input type="text" name="apellido_materno_paciente" required></label>
                      <label>Edad: <input type="number" name="edad_paciente" min="0" required></label>
                      <label>Fecha de Nacimiento: <input type="date" name="fecha_nacimiento_paciente" required></label>
                      <label>Dirección: <input type="text" name="direccion_paciente" required></label>
                    </fieldset>

                    <!-- DATOS DEL TUTOR -->
                    <fieldset>
                      <legend>Datos del Tutor</legend>
                      <label>Nombre: <input type="text" name="nombre_tutor" required></label>
                      <label>Apellido Paterno: <input type="text" name="apellido_paterno_tutor" required></label>
                      <label>Apellido Materno: <input type="text" name="apellido_materno_tutor" required></label>
                      <label>Teléfono: <input type="text" name="telefono_tutor" required></label>
                    </fieldset>

                    <!-- HISTORIAL CLINICO -->
                    <fieldset>
                      <legend>Historial Clínico</legend>
                      <label>Fecha de creación: <input type="date" name="fecha_historial" required></label>
                      <label>Médico: <input type="text" name="medico" required></label>
                    </fieldset>
                  </div>

                  <!------------------------------------------------------------CAMPOS MOTIVO DE LA CONSULTA------------------------------------------------------------------------- -->
                  <div class="parte2">
                    <fieldset>
                      <legend>Motivo de Consulta y Síntomas</legend>

                      <div class="pregunta">
                        <label class="texto-pregunta">Motivo:</label>
                        <textarea name="motivo_consulta" rows="2" required></textarea>
                      </div>

                      <div class="pregunta">
                        <label class="texto-pregunta">Fecha de inicio:</label>
                        <input type="date" name="fecha_inicio_sintomas" required>
                      </div>

                      <fieldset>
                        <legend>Tipo de síntomas</legend>
                        <div class="pregunta">
                          <label><input type="radio" name="tipo_sintomas" value="Esporádicos" required> Esporádicos</label>
                          <label><input type="radio" name="tipo_sintomas" value="Continuos"> Continuos</label>
                        </div>
                      </fieldset>

                      <fieldset>
                        <legend>Presentación durante</legend>
                        <div class="pregunta">
                          <label><input type="radio" name="presentan_durante" value="Temporadas" required> Temporadas</label>
                          <label><input type="radio" name="presentan_durante" value="Todo el año"> Todo el año</label>
                        </div>
                      </fieldset>

                      <fieldset>
                        <legend>Aparecen durante</legend>
                        <div class="pregunta">
                          <label><input type="checkbox" class="multi-sintomas" value="Mañana"> Mañana</label>
                          <label><input type="checkbox" class="multi-sintomas" value="Tarde"> Tarde</label>
                          <label><input type="checkbox" class="multi-sintomas" value="Noche"> Noche</label>
                          <label><input type="checkbox" class="multi-sintomas" value="Todo el día"> Todo el día</label>
                        </div>
                      </fieldset>

                      <!-- DURACION-->
                      <fieldset>
                        <legend>Duración</legend>
                        <div class="pregunta">
                          <label><input type="radio" name="duracion" value="Horas" required> Horas</label>
                          <label><input type="radio" name="duracion" value="Minutos"> Minutos</label>
                          <label><input type="radio" name="duracion" value="Días"> Días</label>
                        </div>
                      </fieldset>

                      <div class="pregunta">
                        <label class="texto-pregunta">Tiempo aproximado:</label>
                        <textarea name="tiempo_aproximado" rows="1" required></textarea>
                        <label class="texto-pregunta">Han sufrido cambios con el tiempo:</label>
                        <textarea name="cambios_tiempo" rows="2" required></textarea>
                        <label class="texto-pregunta">¿Aparecen cuándo?:</label>
                        <textarea name="aparecen_cuando" rows="2" required></textarea>
                      </div>

                      <!-- FUERA -->
                      <fieldset>
                        <legend>Afuera</legend>
                        <div class="pregunta">
                          <label><input type="checkbox" class="multi-afuera" value="Corta pasto"> Corta pasto</label>
                          <label><input type="checkbox" class="multi-afuera" value="Ejercicio"> Ejercicio</label>
                          <label><input type="checkbox" class="multi-afuera" value="Cerca de hojas o basura quemándose"> Cerca de hojas o basura quemándose</label>
                          <label><input type="checkbox" class="multi-afuera" value="Cerca de establos o ranchos"> Cerca de establos o ranchos</label>
                          <label><input type="checkbox" class="multi-afuera" value="Jardines"> Jardines</label>
                        </div>
                        <div class="pregunta">
                          <label class="texto-pregunta">Seleccionados:</label>
                          <textarea name="afuera_text" rows="2" readonly></textarea>
                        </div>
                      </fieldset>

                      <!-- DENTRO -->
                      <fieldset>
                        <legend>Dentro</legend>
                        <div class="pregunta">
                          <label><input type="checkbox" class="multi-dentro" value="Dormitorio"> Dormitorio</label>
                          <label><input type="checkbox" class="multi-dentro" value="Cocina"> Cocina</label>
                          <label><input type="checkbox" class="multi-dentro" value="Otro cuarto"> Otro cuarto</label>
                          <label><input type="checkbox" class="multi-dentro" value="Bodega"> Bodega</label>
                          <label><input type="checkbox" class="multi-dentro" value="Escuela o trabajo"> Escuela o trabajo</label>
                        </div>
                        <div class="pregunta">
                          <label class="texto-pregunta">Seleccionados:</label>
                          <textarea name="dentro_text" rows="2" readonly></textarea>
                        </div>
                      </fieldset>

                      <!-- OCUPACION-->
                      <div class="pregunta">
                        <label class="texto-pregunta">Ocupación:</label>
                        <textarea name="ocupacion" rows="1" required></textarea>
                      </div>

                      <!-- ANTECEDENTES FAMILIARES -->
                      <fieldset>
                        <legend>Antecedentes Familiares</legend>
                        <div class="pregunta">
                          <label>Alguien en la familia ha presentado alergias:</label>
                          <textarea name="antecedentes_familiares" rows="2" required></textarea>
                        </div>
                      </fieldset>

                      <!-- ALERGIAS GENERALES -->
                      <fieldset>
                        <legend>Alergias Generales</legend>
                        <div class="pregunta">
                          <label><input type="checkbox" class="multi-alergias" value="Urticaria"> Urticaria</label>
                          <label><input type="checkbox" class="multi-alergias" value="Salpullido"> Salpullido</label>
                          <label><input type="checkbox" class="multi-alergias" value="Fiebre frecuente"> Fiebre frecuente</label>
                          <label><input type="checkbox" class="multi-alergias" value="Fatiga"> Fatiga</label>
                          <label><input type="checkbox" class="multi-alergias" value="Dolores generales"> Dolores generales</label>
                          <label><input type="checkbox" class="multi-alergias" value="Dolores de cabeza"> Dolores de cabeza</label>
                        </div>
                        <div class="pregunta">
                          <label class="texto-pregunta">Seleccionados:</label>
                          <textarea name="alergias_text" rows="2" readonly></textarea>
                        </div>
                      </fieldset>


                      <!-- APARATO DIGESTIVO -->
                      <fieldset>
                        <legend>Aparato Digestivo</legend>

                        <div class="pregunta">
                          <label><input type="checkbox" class="multi-digestivo" value="Náuseas" /> Náuseas</label>
                          <label><input type="checkbox" class="multi-digestivo" value="Vómito" /> Vómito</label>
                          <label><input type="checkbox" class="multi-digestivo" value="Diarrea" /> Diarrea</label>
                          <label><input type="checkbox" class="multi-digestivo" value="Estreñimiento" /> Estreñimiento</label>
                          <label><input type="checkbox" class="multi-digestivo" value="Dolor abdominal" /> Dolor abdominal</label>
                        </div>

                        <div class="pregunta">
                          <label class="texto-pregunta">Otros:</label>
                          <textarea name="digestivo_otros" rows="2" readonly></textarea>
                          <label class="texto-pregunta">Seleccionados:</label>
                          <textarea name="digestivo_text" rows="2" readonly></textarea>
                        </div>
                      </fieldset>

                      <!-- OJOS -->
                      <fieldset>
                        <legend>Ojos</legend>
                        <div class="pregunta">
                          <label><input type="checkbox" class="multi-ojos" value="Comezón" /> Comezón</label>
                          <label><input type="checkbox" class="multi-ojos" value="Lagrimeo" /> Lagrimeo</label>
                          <label><input type="checkbox" class="multi-ojos" value="Hinchazón alrededor del ojo" /> Hinchazón alrededor del ojo</label>
                          <label><input type="checkbox" class="multi-ojos" value="Ojos rojos" /> Ojos rojos</label>
                          <label><input type="checkbox" class="multi-ojos" value="Conjuntivitis" /> Conjuntivitis</label>
                        </div>

                        <div class="pregunta">
                          <label>Seleccionados:</label>
                          <textarea name="ojos_text" rows="2" readonly></textarea>
                        </div>
                      </fieldset>


                      <!-- OÍDOS -->
                      <fieldset>
                        <legend>Oídos</legend>
                        <div class="pregunta">
                          <label><input type="checkbox" class="multi-oidos" value="Mucosidad clara" /> Mucosidad clara</label>
                          <label><input type="checkbox" class="multi-oidos" value="Mucosidad color" /> Mucosidad color</label>
                          <label><input type="checkbox" class="multi-oidos" value="Comezón" /> Comezón</label>
                          <label><input type="checkbox" class="multi-oidos" value="Estornudos ocasionales" /> Estornudos ocasionales</label>
                          <label><input type="checkbox" class="multi-oidos" value="Estornudos frecuentes" /> Estornudos frecuentes</label>
                          <label><input type="checkbox" class="multi-oidos" value="Nariz tapada" /> Nariz tapada</label>
                          <label><input type="checkbox" class="multi-oidos" value="Pérdida de olfato" /> Pérdida de olfato</label>
                        </div>

                        <div class="pregunta">
                          <label>Seleccionados:</label>
                          <textarea name="oidos_text" rows="2" readonly></textarea>
                        </div>
                      </fieldset>

                      <!-- GARGANTA -->
                      <fieldset>
                        <legend>Garganta</legend>
                        <div class="pregunta">
                          <label><input type="checkbox" class="multi-garganta" value="Dolor" /> Dolor</label>
                          <label><input type="checkbox" class="multi-garganta" value="Picazón" /> Picazón</label>
                          <label><input type="checkbox" class="multi-garganta" value="Ronquera" /> Ronquera</label>
                          <label><input type="checkbox" class="multi-garganta" value="Flemas" /> Flemas</label>
                          <label><input type="checkbox" class="multi-garganta" value="Infecciones frecuentes" /> Infecciones frecuentes</label>
                        </div>

                        <div class="pregunta">
                          <label>Seleccionados:</label>
                          <textarea name="garganta_text" rows="2" readonly></textarea>
                        </div>
                      </fieldset>

                      <!-- TÓRAX -->
                      <fieldset>
                        <legend>Tórax</legend>
                        <div class="pregunta">
                          <label><input type="checkbox" class="multi-torax" value="Tos seca" /> Tos seca</label>
                          <label><input type="checkbox" class="multi-torax" value="Tos con expectoración" /> Tos con expectoración</label>
                          <label><input type="checkbox" class="multi-torax" value="Aparece con frío" /> Aparece con frío</label>
                          <label><input type="checkbox" class="multi-torax" value="Aparece con pólenes" /> Aparece con pólenes</label>
                          <label><input type="checkbox" class="multi-torax" value="Aparece con animales" /> Aparece con animales</label>
                          <label><input type="checkbox" class="multi-torax" value="Presenta molestias leves" /> Presenta molestias leves</label>
                          <label><input type="checkbox" class="multi-torax" value="Presenta molestias fuertes" /> Presenta molestias fuertes</label>
                          <label><input type="checkbox" class="multi-torax" value="Dificultad al respirar" /> Dificultad al respirar</label>
                          <label><input type="checkbox" class="multi-torax" value="Pecho congestionado" /> Pecho congestionado</label>
                        </div>

                        <div class="pregunta">
                          <label>Seleccionados:</label>
                          <textarea name="torax_text" rows="2" readonly></textarea>
                        </div>
                      </fieldset>

                      <!-- MASCOTAS -->
                      <fieldset>
                        <legend>Mascotas</legend>

                        <div class="pregunta">
                          <label>¿Tiene animales en casa?</label>
                          <label><input type="radio" name="mascotas_casa" value="Sí" required /> Sí</label>
                          <label><input type="radio" name="mascotas_casa" value="No" /> No</label>
                        </div>

                        <div class="pregunta">
                          <label>¿Visita casas con animales?</label>
                          <label><input type="radio" name="visita_animales" value="Sí" required /> Sí</label>
                          <label><input type="radio" name="visita_animales" value="No" /> No</label>
                        </div>

                        <div class="pregunta">
                          <label>Señale cuáles animales:</label>
                          <label><input type="checkbox" class="multi-mascotas" value="Gato" /> Gato</label>
                          <label><input type="checkbox" class="multi-mascotas" value="Perro" /> Perro</label>
                          <label><input type="checkbox" class="multi-mascotas" value="Pájaros" /> Pájaros</label>
                          <label><input type="checkbox" class="multi-mascotas" value="Palomas" /> Palomas</label>
                          <label><input type="checkbox" class="multi-mascotas" value="Conejo" /> Conejo</label>
                          <label><input type="checkbox" class="multi-mascotas" value="Caballo" /> Caballo</label>
                          <label><input type="checkbox" class="multi-mascotas" value="Hámster" /> Hámster</label>
                          <label><input type="checkbox" class="multi-mascotas" value="Vacas" /> Vacas</label>
                        </div>

                        <div class="pregunta">
                          <label>Otros:</label>
                          <textarea name="mascotas_otros" rows="1"></textarea>

                          <label>Seleccionados:</label>
                          <textarea name="mascotas_text" rows="2" readonly></textarea>
                        </div>
                      </fieldset>

                      <!-- PICADURAS DE INSECTOS -->
                      <fieldset>
                        <legend>Picaduras de insectos</legend>

                        <div class="pregunta">
                          <label>¿Ha sufrido picaduras?</label>
                          <label><input type="radio" name="picaduras" value="Sí" required /> Sí</label>
                          <label><input type="radio" name="picaduras" value="No" /> No</label>
                        </div>

                        <div class="pregunta">
                          <label>Reacción:</label>
                          <label><input type="radio" name="reaccion_picaduras" value="Severa" /> Severa</label>
                          <label><input type="radio" name="reaccion_picaduras" value="Leve" /> Leve</label>
                        </div>

                        <div class="pregunta">
                          <label>Explique:</label>
                          <textarea name="reaccion_explicacion" rows="2"></textarea>
                        </div>

                        <div class="pregunta">
                          <label>Clase de animal:</label>
                          <label><input type="checkbox" class="multi-insectos" value="Avispa" /> Avispa</label>
                          <label><input type="checkbox" class="multi-insectos" value="Pulga" /> Pulga</label>
                          <label><input type="checkbox" class="multi-insectos" value="Mosquito" /> Mosquito</label>
                          <label><input type="checkbox" class="multi-insectos" value="Abeja de miel" /> Abeja de miel</label>
                        </div>

                        <div class="pregunta">
                          <label>Otros:</label>
                          <textarea name="insectos_otros" rows="1"></textarea>

                          <label>Seleccionados:</label>
                          <textarea name="insectos_text" rows="2" readonly></textarea>
                        </div>
                      </fieldset>

                      <!-- MEDICAMENTOS -->
                      <fieldset>
                        <legend>Medicamentos</legend>

                        <div class="pregunta">
                          <label>¿Toma algún medicamento?</label>
                          <label><input type="checkbox" class="multi-medicamentos" value="Aspirina" /> Aspirina</label>
                          <label><input type="checkbox" class="multi-medicamentos" value="Anticonceptivo" /> Anticonceptivo</label>
                          <label><input type="checkbox" class="multi-medicamentos" value="Antihipertensivo" /> Antihipertensivo</label>
                          <label><input type="checkbox" class="multi-medicamentos" value="Sedante" /> Sedante</label>
                          <label><input type="checkbox" class="multi-medicamentos" value="Vitaminas" /> Vitaminas</label>
                          <label><input type="checkbox" class="multi-medicamentos" value="Hormonas" /> Hormonas</label>
                        </div>

                        <div class="pregunta">
                          <label>Otros:</label>
                          <textarea name="medicamentos_otros" rows="1"></textarea>

                          <label>Seleccionados:</label>
                          <textarea name="medicamentos_text" rows="2" readonly></textarea>
                        </div>

                        <div class="pregunta">
                          <label>¿Ha presentado reacción alérgica a algún medicamento?</label>
                          <label><input type="radio" name="reaccion_medicamento" value="Sí" /> Sí</label>
                          <label><input type="radio" name="reaccion_medicamento" value="No" /> No</label>
                        </div>

                        <div class="pregunta">
                          <label>Si sí, indique cuál:</label>
                          <textarea name="reaccion_medicamento_detalle" rows="2"></textarea>
                        </div>
                      </fieldset>

                      <!-- ALERGIAS POR SUSTANCIAS -->
                      <fieldset>
                        <legend>Alergias por Sustancias</legend>

                        <div class="pregunta">
                          <label>Alguna sustancia le provoca o agrava los síntomas:</label>

                          <label><input type="checkbox" class="multi-sustancias" value="Jabón de tocador" /> Jabón de tocador</label>
                          <label><input type="checkbox" class="multi-sustancias" value="Detergente" /> Detergente</label>
                          <label><input type="checkbox" class="multi-sustancias" value="Shampoo" /> Shampoo</label>
                          <label><input type="checkbox" class="multi-sustancias" value="Algodón" /> Algodón</label>
                          <label><input type="checkbox" class="multi-sustancias" value="Perfumes" /> Perfumes</label>
                          <label><input type="checkbox" class="multi-sustancias" value="Cremas" /> Cremas</label>
                          <label><input type="checkbox" class="multi-sustancias" value="Periódicos" /> Periódicos</label>
                          <label><input type="checkbox" class="multi-sustancias" value="Revistas" /> Revistas</label>
                          <label><input type="checkbox" class="multi-sustancias" value="Tela" /> Tela</label>
                          <label><input type="checkbox" class="multi-sustancias" value="Productos de limpieza" /> Productos de limpieza</label>
                        </div>

                        <div class="pregunta">
                          <label>Otros:</label>
                          <textarea name="sustancias_otros" rows="1"></textarea>

                          <label>Seleccionados:</label>
                          <textarea name="sustancias_text" rows="2" readonly></textarea>
                        </div>
                      </fieldset>


                      <!-- FRECUENCIA DE ALIMENTOS -->
                      <fieldset>
                        <legend>Frecuencia de alimentos</legend>
                        <table border="1" class="tabla-alimentos">
                          <thead>
                          <tr>
                            <th>Alimento</th>
                            <th>Semanal</th>
                            <th>Diario</th>
                            <th>Raro</th>
                            <th>Nunca</th>
                          </tr>
                          </thead>

                          <tbody>
                          <tr>
                            <td>Huevo</td>
                            <td><input type="radio" name="huevo" value="Semanal" /></td>
                            <td><input type="radio" name="huevo" value="Diario" /></td>
                            <td><input type="radio" name="huevo" value="Raro" /></td>
                            <td><input type="radio" name="huevo" value="Nunca" /></td>
                          </tr>

                          <tr>
                            <td>Trigo (Pan)</td>
                            <td><input type="radio" name="trigo" value="Semanal" /></td>
                            <td><input type="radio" name="trigo" value="Diario" /></td>
                            <td><input type="radio" name="trigo" value="Raro" /></td>
                            <td><input type="radio" name="trigo" value="Nunca" /></td>
                          </tr>

                          <tr>
                            <td>Maíz</td>
                            <td><input type="radio" name="maiz" value="Semanal" /></td>
                            <td><input type="radio" name="maiz" value="Diario" /></td>
                            <td><input type="radio" name="maiz" value="Raro" /></td>
                            <td><input type="radio" name="maiz" value="Nunca" /></td>
                          </tr>

                          <tr>
                            <td>Chocolate</td>
                            <td><input type="radio" name="chocolate" value="Semanal" /></td>
                            <td><input type="radio" name="chocolate" value="Diario" /></td>
                            <td><input type="radio" name="chocolate" value="Raro" /></td>
                            <td><input type="radio" name="chocolate" value="Nunca" /></td>
                          </tr>

                          <tr>
                            <td>Cacahuate</td>
                            <td><input type="radio" name="cacahuate" value="Semanal" /></td>
                            <td><input type="radio" name="cacahuate" value="Diario" /></td>
                            <td><input type="radio" name="cacahuate" value="Raro" /></td>
                            <td><input type="radio" name="cacahuate" value="Nunca" /></td>
                          </tr>

                          <tr>
                            <td>Naranja</td>
                            <td><input type="radio" name="naranja" value="Semanal" /></td>
                            <td><input type="radio" name="naranja" value="Diario" /></td>
                            <td><input type="radio" name="naranja" value="Raro" /></td>
                            <td><input type="radio" name="naranja" value="Nunca" /></td>
                          </tr>

                          <tr>
                            <td>Soya</td>
                            <td><input type="radio" name="soya" value="Semanal" /></td>
                            <td><input type="radio" name="soya" value="Diario" /></td>
                            <td><input type="radio" name="soya" value="Raro" /></td>
                            <td><input type="radio" name="soya" value="Nunca" /></td>
                          </tr>

                          <tr>
                            <td>Puerco</td>
                            <td><input type="radio" name="puerco" value="Semanal" /></td>
                            <td><input type="radio" name="puerco" value="Diario" /></td>
                            <td><input type="radio" name="puerco" value="Raro" /></td>
                            <td><input type="radio" name="puerco" value="Nunca" /></td>
                          </tr>

                          <tr>
                            <td>Carne de res</td>
                            <td><input type="radio" name="carne_res" value="Semanal" /></td>
                            <td><input type="radio" name="carne_res" value="Diario" /></td>
                            <td><input type="radio" name="carne_res" value="Raro" /></td>
                            <td><input type="radio" name="carne_res" value="Nunca" /></td>
                          </tr>

                          <tr>
                            <td>Atún</td>
                            <td><input type="radio" name="atun" value="Semanal" /></td>
                            <td><input type="radio" name="atun" value="Diario" /></td>
                            <td><input type="radio" name="atun" value="Raro" /></td>
                            <td><input type="radio" name="atun" value="Nunca" /></td>
                          </tr>

                          <tr>
                            <td>Camarones</td>
                            <td><input type="radio" name="camarones" value="Semanal" /></td>
                            <td><input type="radio" name="camarones" value="Diario" /></td>
                            <td><input type="radio" name="camarones" value="Raro" /></td>
                            <td><input type="radio" name="camarones" value="Nunca" /></td>
                          </tr>

                          <tr>
                            <td>Pescado</td>
                            <td><input type="radio" name="pescado" value="Semanal" /></td>
                            <td><input type="radio" name="pescado" value="Diario" /></td>
                            <td><input type="radio" name="pescado" value="Raro" /></td>
                            <td><input type="radio" name="pescado" value="Nunca" /></td>
                          </tr>

                          <tr>
                            <td>Arroz</td>
                            <td><input type="radio" name="arroz" value="Semanal" /></td>
                            <td><input type="radio" name="arroz" value="Diario" /></td>
                            <td><input type="radio" name="arroz" value="Raro" /></td>
                            <td><input type="radio" name="arroz" value="Nunca" /></td>
                          </tr>

                          <tr>
                            <td>Cereal</td>
                            <td><input type="radio" name="cereal" value="Semanal" /></td>
                            <td><input type="radio" name="cereal" value="Diario" /></td>
                            <td><input type="radio" name="cereal" value="Raro" /></td>
                            <td><input type="radio" name="cereal" value="Nunca" /></td>
                          </tr>

                          <tr>
                            <td>Papa</td>
                            <td><input type="radio" name="papa" value="Semanal" /></td>
                            <td><input type="radio" name="papa" value="Diario" /></td>
                            <td><input type="radio" name="papa" value="Raro" /></td>
                            <td><input type="radio" name="papa" value="Nunca" /></td>
                          </tr>

                          </tbody>
                        </table>
                      </fieldset>

                      <!-- PREGUNTAS SÍ / NO -->
                      <fieldset>
                        <legend>Preguntas Sí/No</legend>

                        <div class="pregunta">
                          <label>¿Ha presentado fiebre recientemente?</label>
                          <label><input type="radio" name="fiebre" value="Sí" /> Sí</label>
                          <label><input type="radio" name="fiebre" value="No" /> No</label>
                        </div>

                        <div class="pregunta">
                          <label>¿Ha tenido tos persistente?</label>
                          <label><input type="radio" name="tos" value="Sí" /> Sí</label>
                          <label><input type="radio" name="tos" value="No" /> No</label>
                        </div>

                        <div class="pregunta">
                          <label>¿Ha presentado dificultad para respirar?</label>
                          <label><input type="radio" name="dificultad_respirar" value="Sí" /> Sí</label>
                          <label><input type="radio" name="dificultad_respirar" value="No" /> No</label>
                        </div>
                      </fieldset>

                      <!-- TRATAMIENTOS PREVIOS -->
                      <fieldset>
                        <legend>Tratamientos previos</legend>
                        <div class="pregunta">
                          <label>Tratamientos recibidos:</label>
                          <textarea name="tratamientos_previos" rows="2"></textarea>
                        </div>
                      </fieldset>

                      <!-- PLANTAS -->
                      <fieldset>
                        <legend>Plantas</legend>
                        <div class="pregunta">
                          <label>Uso de plantas medicinales:</label>
                          <textarea name="plantas" rows="2"></textarea>
                        </div>
                      </fieldset>

                      <!-- COMENTARIOS FINALES -->
                      <fieldset>
                        <legend>Comentarios finales</legend>
                        <div class="pregunta">
                          <label>Comentarios adicionales:</label>
                          <textarea name="comentarios_finales" rows="3"></textarea>
                        </div>
                      </fieldset>
                    </fieldset>  <!fin general de los campos¡>
                  </div>
                </div>

                <div class="form-buttons">
                  <button type="button" id="btn-edit" class="btn btn-primary"><i class="fas fa-pen"></i> Editar</button>
                  <button type="button" id="btn-delete" class="btn btn-danger"><i class="fas fa-trash"></i> Eliminar</button>
                </div>
              </div>
            </form>
          </div>
        </section>
      </main>
    </div>
  </div>
</template>

<!---------------------------------------------------FUNCIONES ------------------------------------------------------->
<script setup>
import { onMounted } from 'vue'
import '@/style_h_clinica.css'

onMounted(() => {
  function setupMultiSelect(className, textareaName) {
    const checkboxes = document.querySelectorAll(`.${className}`)
    const textarea = document.querySelector(`textarea[name="${textareaName}"]`)
    if (!checkboxes || !textarea) return
    checkboxes.forEach(cb => {
      cb.addEventListener('change', () => {
        const selected = Array.from(checkboxes).filter(chk => chk.checked).map(chk => chk.value)
        textarea.value = selected.join(', ')
      })
    })
  }

  ;[
    'multi-mascotas', 'multi-insectos', 'multi-medicamentos', 'multi-sustancias',
    'multi-sintomas', 'multi-afuera', 'multi-dentro', 'multi-alergias',
    'multi-ojos', 'multi-oidos', 'multi-garganta', 'multi-torax'
  ].forEach(cls => {
    setupMultiSelect(cls, cls.replace('multi-', '') + '_text')
  })

  const form = document.getElementById('form-h-clinica')
  if (form) form.style.display = 'flex'

  const fill = (name, value) => {
    const el = document.querySelector(`[name="${name}"]`)
    if (el) el.value = value
  }

  fill("nombre_paciente", " ")
  fill("apellido_paterno_paciente", " ")
  fill("apellido_materno_paciente", " ")
  fill("edad_paciente", " ")
  fill("fecha_nacimiento_paciente", " ")
  fill("direccion_paciente", " ")
  fill("nombre_tutor", " ")
  fill("apellido_paterno_tutor", " ")
  fill("apellido_materno_tutor", " ")
  fill("telefono_tutor", " ")
  fill("fecha_historial", " ")
  fill("medico", " ")
  fill("motivo_consulta", " ")
  fill("tiempo_aproximado", " ")
  fill("cambios_tiempo", " ")
  fill("aparecen_cuando", " ")

  const check = (sel) => {
    const el = document.querySelector(sel)
    if (el) el.checked = true
  }
  check('input[name="tipo_sintomas"][value="Esporádicos"]')
  check('input[name="presentan_durante"][value="Temporadas"]')
  check('input[name="duracion"][value="Días"]')

  function setReadOnlyMode(isReadOnly) {
    document.querySelectorAll('#form-h-clinica input, #form-h-clinica textarea')
        .forEach(el => {
          el.readOnly = isReadOnly
          el.disabled = isReadOnly && (el.type === 'radio' || el.type === 'checkbox')
        })
  }

  setReadOnlyMode(true)

  const btnEdit = document.getElementById('btn-edit')
  if (btnEdit) {
    btnEdit.addEventListener('click', () => {
      const firstInput = document.querySelector('#form-h-clinica input')
      if (!firstInput) return
      const isReadOnly = firstInput.readOnly
      setReadOnlyMode(!isReadOnly)
      btnEdit.innerHTML = isReadOnly
          ? '<i class="fas fa-eye"></i> Ver'
          : '<i class="fas fa-pen"></i> Editar'
    })
  }

  const btnDelete = document.getElementById('btn-delete')
  if (btnDelete) {
    btnDelete.addEventListener('click', () => {
      if (confirm('¿Desea eliminar los datos de este paciente?')) {
        const formEl = document.getElementById('form-h-clinica')
        if (formEl) formEl.reset()
        alert('Datos eliminados.')
      }
    })
  }
})
</script>

<!-- AGREGA EL ESTILO DEL ARCHIVO style_h_clinica -->
<style>
.gestion-h-clinica-root .main-content { min-height: 100vh; }
</style>
