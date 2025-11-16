<template>
  <main class="content">
    <table class="antigen-table">
      <thead>
      <tr>
        <th colspan="1">Antígenos</th>
        <th colspan="1">Resultados en Piel</th>
        <th colspan="1">Resultados Skin End Point</th>
        <th colspan="4">Tratamiento</th>
      </tr>
      <tr>
        <th colspan="3" style="background-color: #ffffff;"></th>
        <th colspan="2">Frasco 1</th>
        <th colspan="2">Frasco 2</th>
      </tr>
      <tr>
        <th colspan="3" style="background-color: #ffffff;"></th>
        <th>Número</th>
        <th>Mililitro</th>
        <th>Número</th>
        <th>Mililitro</th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td>Caballo</td>
        <td>+++</td>
        <td>1:500</td>
        <td><input type="number" placeholder="#"></td>
        <td><input type="number" step="0.1" placeholder="ml"></td>
        <td><input type="number" placeholder="#"></td>
        <td><input type="number" step="0.1" placeholder="ml"></td>
      </tr>
      <tr>
        <td>Pólene</td>
        <td>++</td>
        <td>1:1000</td>
        <td><input type="number" placeholder="#"></td>
        <td><input type="number" step="0.1" placeholder="ml"></td>
        <td><input type="number" placeholder="#"></td>
        <td><input type="number" step="0.1" placeholder="ml"></td>
      </tr>
      <tr>
        <td>Perro</td>
        <td>++++</td>
        <td>1:250</td>
        <td><input type="number" placeholder="#"></td>
        <td><input type="number" step="0.1" placeholder="ml"></td>
        <td><input type="number" placeholder="#"></td>
        <td><input type="number" step="0.1" placeholder="ml"></td>
      </tr>
      <tr>
        <td>Hongos</td>
        <td>++++</td>
        <td>1:250</td>
        <td><input type="number" placeholder="#"></td>
        <td><input type="number" step="0.1" placeholder="ml"></td>
        <td><input type="number" placeholder="#"></td>
        <td><input type="number" step="0.1" placeholder="ml"></td>
      </tr>
      <tr>
        <td>Gato</td>
        <td>++</td>
        <td>1:1000</td>
        <td><input type="number" placeholder="#"></td>
        <td><input type="number" step="0.1" placeholder="ml"></td>
        <td><input type="number" placeholder="#"></td>
        <td><input type="number" step="0.1" placeholder="ml"></td>
      </tr>
      </tbody>
      <tfoot>
      <tr>
        <td colspan="3"><strong>Antígeno</strong></td>
        <td colspan="2"><input type="number" placeholder="#"></td>
        <td colspan="2"><input type="number" placeholder="#"></td>
      </tr>
      <tr>
        <td colspan="3"><strong>Diluyente</strong></td>
        <td colspan="2"><input type="number" placeholder="#"></td>
        <td colspan="2"><input type="number" placeholder="#"></td>
      </tr>
      <tr>
        <td colspan="3"><strong>Total</strong></td>
        <td colspan="2"><input type="number" placeholder="#"></td>
        <td colspan="2"><input type="number" placeholder="#"></td>
      </tr>
      </tfoot>
    </table>

    <div class="btn-container">
      <button id="generar-historial" class="btn btn-historial" @click="verHistorial">
        <i class="fas fa-history"></i> Ver historial de vacunación
      </button>
      <button id="generate-report-btn" class="btn btn-reporte-vacunacion" @click="generarReporte">
        <i class="fas fa-file-alt"></i> Generar reporte de vacunación
      </button>
      <button class="btn btn-resultados-paciente" @click="generarResultados">
        <i class="fas fa-file-pdf"></i> Generar resultados paciente
      </button>
    </div>
  </main>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';

const router = useRouter();

const verHistorial = () => {
  // Redirigir a la vista de historial de vacunación cuando exista
  // router.push({ name: 'VacunasHistorial' });
  window.location.href = 'Vacunas_Historial.html';
};

const generarReporte = () => {
  const originalTable = document.querySelector('.antigen-table') as HTMLElement;
  const tableForReport = originalTable.cloneNode(true) as HTMLElement;
  const inputs = tableForReport.querySelectorAll('input');

  inputs.forEach(input => {
    const value = (input as HTMLInputElement).value || ' ';
    const parentCell = input.parentNode as HTMLElement;
    parentCell.textContent = value;
  });

  const reportContent = document.createElement('div');
  reportContent.id = 'report-content';

  const title = document.createElement('h2');
  title.textContent = 'Reporte de Pruebas y Tratamiento';

  const date = document.createElement('p');
  date.textContent = `Fecha de Generación: ${new Date().toLocaleDateString('es-ES')}`;

  reportContent.appendChild(title);
  reportContent.appendChild(date);
  reportContent.appendChild(tableForReport);

  const options = {
    margin: 0.5,
    filename: 'reporte_de_vacunacion.pdf',
    image: { type: 'jpeg', quality: 0.98 },
    html2canvas: { scale: 2 },
    jsPDF: { unit: 'in', format: 'letter', orientation: 'portrait' }
  };

  // @ts-ignore - html2pdf is loaded via CDN
  html2pdf().from(reportContent).set(options).save();
};

const generarResultados = () => {
  window.open('../Documentos/Esquema_Inicial.pdf');
};
</script>

<style scoped>
/* Estilos específicos para los botones de vacunación */
.btn-historial {
  background-color: #6c757d;
  color: white;
}

.btn-historial:hover {
  background-color: #5a6268;
}

.btn-reporte-vacunacion {
  background-color: #17a2b8;
  color: white;
}

.btn-reporte-vacunacion:hover {
  background-color: #138496;
}

.btn-resultados-paciente {
  background-color: #28a745;
  color: white;
}

.btn-resultados-paciente:hover {
  background-color: #218838;
}

/* Asegurar que la tabla tenga buen contraste */
.antigen-table td {
  color: #2c3e50;
  font-weight: 500;
}

.antigen-table th {
  background-color: var(--primary-color);
  color: white;
}
</style>