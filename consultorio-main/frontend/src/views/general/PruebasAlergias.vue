<template>
  <div>
    <div class="coming-soon">
      <h2>Pruebas de Alergias</h2>
      <p>Seleccione la Caja de pricker para gestionar los resultados</p>

      <!-- Sección de cancelación -->
      <div v-if="showCancelSection" class="cancel-reason">
        <h3><i class="fas fa-times-circle"></i> Cancelar Prueba</h3>
        <p>Seleccione el motivo de cancelación:</p>

        <div class="reason-options">
          <label
              v-for="reason in cancelReasons"
              :key="reason.value"
              class="reason-option"
          >
            <input
                type="radio"
                v-model="selectedCancelReason"
                :value="reason.value"
            >
            <span>{{ reason.label }}</span>
          </label>
        </div>

        <div v-if="selectedCancelReason === 'otro'" class="other-reason">
          <textarea
              v-model="otherReasonText"
              placeholder="Especifique el motivo de cancelación..."
          ></textarea>
        </div>

        <div class="cancel-actions">
          <button class="btn btn-secondary" @click="volverAPrueba">
            <i class="fas fa-arrow-left"></i> Volver a la Prueba
          </button>
          <button class="btn btn-danger" @click="confirmarCancelacion">
            <i class="fas fa-check"></i> Confirmar Cancelación
          </button>
        </div>
      </div>

      <!-- Contenido normal de la prueba -->
      <div v-if="!showCancelSection">
        <div class="tab-container">
          <div class="tabs">
            <button
                :class="['tab-btn', { active: activeTab === 'tab1' }]"
                @click="activeTab = 'tab1'"
            >
              Caja Pricker 1
            </button>
            <button
                :class="['tab-btn', { active: activeTab === 'tab2' }]"
                @click="activeTab = 'tab2'"
            >
              Caja Pricker 2
            </button>
          </div>

          <!-- Tab 1 -->
          <div v-show="activeTab === 'tab1'" class="tab-content active">
            <h3>Caja Pricker 1</h3>
            <div class="tables-grid">
              <div
                  v-for="(group, groupIndex) in antigenosGroupsCaja1"
                  :key="groupIndex"
                  class="mini-table"
              >
                <h4>Antígenos {{ group.start }} - {{ group.end }}</h4>
                <table>
                  <thead>
                  <tr>
                    <th>#</th>
                    <th>Antígeno</th>
                    <th>Score</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-for="(antigeno, index) in group.items" :key="index">
                    <td>{{ antigeno.number }}</td>
                    <td>{{ antigeno.name }}</td>
                    <td>
                      <input
                          type="number"
                          v-model.number="antigeno.score"
                          min="0"
                          step="0.1"
                      >
                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>
            <div class="btn-container">
              <button class="btn btn-primary" @click="guardarDatos(1)">
                <i class="fas fa-save"></i> Guardar Caja 1
              </button>
            </div>
          </div>

          <!-- Tab 2 -->
          <div v-show="activeTab === 'tab2'" class="tab-content active">
            <h3>Caja Pricker 2</h3>
            <div class="tables-grid">
              <div
                  v-for="(group, groupIndex) in antigenosGroupsCaja2"
                  :key="groupIndex"
                  class="mini-table"
              >
                <h4>Antígenos {{ group.start }} - {{ group.end }}</h4>
                <table>
                  <thead>
                  <tr>
                    <th>#</th>
                    <th>Antígeno</th>
                    <th>Score</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-for="(antigeno, index) in group.items" :key="index">
                    <td>{{ antigeno.number }}</td>
                    <td>{{ antigeno.name }}</td>
                    <td>
                      <input
                          type="number"
                          v-model.number="antigeno.score"
                          min="0"
                          step="0.1"
                      >
                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>
            <div class="btn-container">
              <button class="btn btn-primary" @click="guardarDatos(2)">
                <i class="fas fa-save"></i> Guardar Caja 2
              </button>
              <button class="btn btn-success" @click="generarReporte">
                <i class="fas fa-file-pdf"></i> Reporte de Resultados
              </button>
              <button class="btn btn-warning" @click="generarReporteVacunas">
                <i class="fas fa-file-pdf"></i> Reporte de Vacunas
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

// Estado
const showCancelSection = ref(false);
const activeTab = ref('tab1');
const selectedCancelReason = ref('');
const otherReasonText = ref('');

// Motivos de cancelación
const cancelReasons = [
  { value: 'paciente_no_asistio', label: 'Paciente no se presentó' },
  { value: 'paciente_enfermo', label: 'Paciente enfermo' },
  { value: 'reactivos_faltantes', label: 'Faltan reactivos o materiales' },
  { value: 'urgencia_medica', label: 'Urgencia médica' },
  { value: 'otro', label: 'Otro motivo' }
];

// Datos de antígenos Caja 1
const antigenosCaja1 = ref([
  "Ganado", "Lana", "Caballo", "Gato", "Perro",
  "Mezcla de Plumas", "Derma Farinae", "Derma Pteronyssinus",
  "Polvo Casero", "Candida Albicans", "Rhizopus Nigricans",
  "Aspergillus Fumigatus", "Penicillium Notatum", "Mucor Racemosus",
  "Mezcla de Cucaracha", "Mosco", "Abeja", "Avispa", "Prosopis sp",
  "Eucalipto sp", "Cynodon Dactylon", "Phleum Pratense",
  "Schinnus Molle", "Acacia Sp", "Populus Alba", "Taraxacum Officinale",
  "Chenopodium Alba", "Fraxinus Uhdei", "Paspalum Notatum", "Lolium Perenne"
]);

// Datos de antígenos Caja 2
const antigenosCaja2 = ref([
  "Pronnus", "Ligustrum Lucidum", "Ambeosia Elatior", "Aguacate",
  "Pavo", "Avena", "Fresa", "Soya", "Tomate", "Res", "Chocolate",
  "Platano", "Naranja", "Arroz", "Queso", "Sandía", "Camarón",
  "Calabaza", "Puerco", "Uva", "Atún", "Manzana", "Pollo", "Maiz",
  "Huevo Yema", "Huevo Clara", "Huachinango", "Cacahuate", "Leche", "Trigo"
]);

// Computed - Agrupar antígenos de 5 en 5
const antigenosGroupsCaja1 = computed(() => {
  const groups = [];
  for (let i = 0; i < antigenosCaja1.value.length; i += 5) {
    const items = antigenosCaja1.value.slice(i, i + 5).map((name, index) => ({
      number: i + index + 1,
      name,
      score: 0
    }));
    groups.push({
      start: i + 1,
      end: i + 5,
      items
    });
  }
  return groups;
});

const antigenosGroupsCaja2 = computed(() => {
  const groups = [];
  for (let i = 0; i < antigenosCaja2.value.length; i += 5) {
    const items = antigenosCaja2.value.slice(i, i + 5).map((name, index) => ({
      number: i + index + 1,
      name,
      score: 0
    }));
    groups.push({
      start: i + 1,
      end: i + 5,
      items
    });
  }
  return groups;
});

// Funciones
const volverAPrueba = () => {
  showCancelSection.value = false;
  selectedCancelReason.value = '';
  otherReasonText.value = '';
};

const confirmarCancelacion = () => {
  if (!selectedCancelReason.value) {
    alert('Por favor seleccione un motivo de cancelación');
    return;
  }

  let reasonText = '';
  const reason = cancelReasons.find(r => r.value === selectedCancelReason.value);

  if (selectedCancelReason.value === 'otro') {
    reasonText = otherReasonText.value || 'Otro motivo no especificado';
  } else {
    reasonText = reason?.label || '';
  }

  console.log('Motivo de cancelación:', reasonText);
  alert(`Prueba cancelada. Motivo: ${reasonText}\n\nRedirigiendo al calendario...`);
  router.push({ name: 'Calendario' });
};

const guardarDatos = (caja: number) => {
  alert(`Datos guardados de Caja Pricker ${caja}`);
  // Aquí puedes enviar los datos al backend
  console.log(`Guardando caja ${caja}:`, caja === 1 ? antigenosGroupsCaja1.value : antigenosGroupsCaja2.value);
};

const generarReporte = () => {
  alert('Generando Reporte de Resultados...');
  // Aquí puedes implementar la generación del PDF
};

const generarReporteVacunas = () => {
  alert('Generando Reporte de Vacunas...');
  // Aquí puedes implementar la generación del PDF de vacunas
};

// Verificar al montar si el paciente es apto
onMounted(() => {
  const apto = confirm('¿Paciente apto para la prueba?');
  showCancelSection.value = !apto;
});
</script>

<style scoped>
.tab-container {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 10px;
  padding: 20px;
  margin-top: 20px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
}

.tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
  border-bottom: 2px solid var(--background-color);
  padding-bottom: 10px;
}

.tab-btn {
  padding: 12px 24px;
  border: none;
  background: #f8f9fa;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
  color: var(--text-color);
  transition: all 0.3s ease;
}

.tab-btn.active {
  background: var(--primary-color);
  color: white;
}

.tab-btn:hover:not(.active) {
  background: var(--secondary-color);
  color: white;
}

.tables-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.mini-table {
  background: white;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border: 1px solid #e6eef6;
}

.mini-table h4 {
  margin: 0 0 15px 0;
  color: var(--primary-color);
  font-size: 16px;
  text-align: center;
  padding-bottom: 10px;
  border-bottom: 1px solid var(--background-color);
}

.mini-table table {
  width: 100%;
  border-collapse: collapse;
}

.mini-table th,
.mini-table td {
  padding: 8px 10px;
  text-align: left;
  border: 1px solid #e6eef6;
  font-size: 14px;
}

.mini-table th {
  background-color: var(--primary-color);
  color: white;
  font-weight: 600;
}

.mini-table tr:nth-child(even) {
  background-color: #f8f9fa;
}

.mini-table input[type="number"] {
  width: 80px;
  padding: 6px 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  text-align: center;
}

.mini-table input[type="number"]:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 5px rgba(56, 168, 214, 0.3);
}

.cancel-reason {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 10px;
  padding: 25px;
  margin-top: 20px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
  border-left: 5px solid var(--accent-color);
}

.cancel-reason h3 {
  color: var(--accent-color);
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.reason-options {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin-bottom: 20px;
}

.reason-option {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px;
  border: 1px solid #e6eef6;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.reason-option:hover {
  background-color: #f8f9fa;
  border-color: var(--primary-color);
}

.reason-option input[type="radio"] {
  margin: 0;
}

.other-reason {
  margin: 15px 0;
}

.other-reason textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid #e6eef6;
  border-radius: 8px;
  resize: vertical;
  min-height: 80px;
  font-family: inherit;
}

.other-reason textarea:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 5px rgba(56, 168, 214, 0.3);
}

.cancel-actions {
  display: flex;
  gap: 15px;
  justify-content: flex-end;
  margin-top: 20px;
}

.btn-container {
  display: flex;
  gap: 15px;
  margin-top: 25px;
  justify-content: flex-end;
  flex-wrap: wrap;
}

@media (max-width: 768px) {
  .tables-grid {
    grid-template-columns: 1fr;
  }

  .tabs {
    flex-direction: column;
  }

  .btn-container {
    flex-direction: column;
  }

  .btn-container .btn {
    width: 100%;
  }
}
</style>