<template>

    <!-- Contenido principal -->
    <main class="content">
      <!-- Tarjeta del paciente -->
      <div class="card-paciente">
        <div class="card-header">
          <h2>Datos del Paciente</h2>
          <div class="patient-id-badge">
            <i class="fas fa-id-card"></i> ID: 001
          </div>
        </div>

        <div class="form-grid">
          <div class="form-group">
            <label>Nombre Completo</label>
            <p>Juan Pérez López</p>
          </div>
          <div class="form-group">
            <label>Fecha de Nacimiento</label>
            <p>12/03/2005</p>
          </div>
          <div class="form-group">
            <label>Edad</label>
            <p>18 años</p>
          </div>
          <div class="form-group">
            <label>Sexo</label>
            <p>Masculino</p>
          </div>
          <div class="form-group">
            <label>Nombre del Tutor</label>
            <p>María López</p>
          </div>
          <div class="form-group">
            <label>Correo del Tutor</label>
            <p>maria.lopez@email.com</p>
          </div>
          <div class="form-group">
            <label>Teléfono del Tutor</label>
            <p>555-123-4567</p>
          </div>
          <div class="form-group">
            <label>Dirección Completa</label>
            <p>Av. Reforma #123, Centro, Ciudad de México, CDMX, 06000</p>
          </div>
        </div>
      </div>

      <!-- Tarjeta de módulos -->
      <div class="card-paciente">
        <h2>Módulos del Paciente</h2>
        <div class="form-actions">
          <router-link to="/historial-clinico" class="btn btn-historial">
            <i class="fas fa-notes-medical"></i> Historial Clínico
          </router-link>
          <router-link to="/seguimiento" class="btn btn-seguimiento">
            <i class="fas fa-syringe"></i> Seguimiento del Tratamiento
          </router-link>
          <router-link to="/pruebas-alergias" class="btn btn-pruebas">
            <i class="fas fa-allergies"></i> Pruebas de Alergia
          </router-link>
          <router-link to="/vacunas" class="btn btn-vacunas">
            <i class="fas fa-syringe"></i> Vacunas Generadas
          </router-link>
          <router-link to="/reportes" class="btn btn-reporte">
            <i class="fas fa-file-pdf"></i> Generar Reporte
          </router-link>
        </div>
      </div>
    </main>
</template>

<script >
import { ref } from 'vue';

export default {
  name: "AdministrarPaciente",
};
</script>

<style scoped>
.card-paciente {
  background-color: #ffffff;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
  margin-bottom: 30px;
  border-left: 5px solid var(--primary-color);
}

.card-paciente h2 {
  margin-bottom: 25px;
  font-size: 24px;
  color: var(--primary-color);
  border-bottom: 2px solid var(--background-color);
  padding-bottom: 12px;
  font-weight: 700;
}

.card-paciente .form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
}

.card-paciente .form-group {
  background: #f8fafc;
  padding: 15px;
  border-radius: 8px;
  border: 1px solid #e9ecef;
  transition: all 0.3s ease;
}
.card-paciente .form-group:hover {
  background: #f1f7fd;
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
}
.card-paciente .form-group label {
  font-weight: 600;
  color: #495057;
  display: block;
  margin-bottom: 8px;
  font-size: 14px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}
.card-paciente .form-group p {
  margin: 0;
  color: #2c3e50;
  font-size: 16px;
  font-weight: 500;
  padding: 8px 0;
}
.card-paciente .form-actions {
  margin-top: 25px;
  display: flex;
  gap: 15px;
  justify-content: center;
  flex-wrap: nowrap;
  overflow-x: auto;
  -webkit-overflow-scrolling: touch;
  padding-bottom: 10px;
}

@media (max-width: 1190px) {
  .card-paciente  .form-actions {
    flex-wrap: wrap;
    overflow-x: visible;
    justify-content: center;
  }
}

/* Botones */
.btn {
  padding: 12px 20px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
  display: inline-flex;
  align-items: center;
  gap: 8px;
  color: white;
  font-size: 14px;
  min-width: 200px;
  justify-content: center;
  text-decoration: none;
}
.btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}
.btn-historial {
  background-color: #17a2b8;
}
.btn-historial:hover {
  background-color: #138496;
}
.btn-seguimiento {
  background-color: #28a745;
}
.btn-seguimiento:hover {
  background-color: #218838;
}
.btn-pruebas {
  background-color: #fcac17;
  color: white;
}
.btn-pruebas:hover {
  background-color: #e0a800;
}
.btn-vacunas {
  background-color: #6f42c1;
}
.btn-vacunas:hover {
  background-color: #5a359c;
}
.btn-reporte {
  background-color: #e74c3c;
}
.btn-reporte:hover {
  background-color: #c0392b;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
.patient-id-badge {
  background: var(--primary-color);
  color: white;
  padding: 8px 16px;
  border-radius: 20px;
  font-weight: 600;
  font-size: 14px;
}


</style>
