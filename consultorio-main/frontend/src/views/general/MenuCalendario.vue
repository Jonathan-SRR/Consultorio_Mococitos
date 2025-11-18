<template>
  <div>
    <!-- Citas de Hoy -->
    <div class="today-appointments">
      <h2>Citas de Hoy</h2>
      <div class="appointments-list">
        <div
            v-for="cita in citasDeHoy"
            :key="cita.id"
            class="appointment-card"
        >
          <span class="appointment-time">{{ cita.hora }}</span>
          <span class="appointment-patient">{{ cita.paciente }}</span>
          <span class="appointment-type">{{ cita.tipo }}</span>
          <button class="comenzar-btn" @click="iniciarCita(cita.tipo)">
            Comenzar
          </button>
        </div>
      </div>
    </div>

    <!-- Calendario -->
    <div id="calendar-container">
      <FullCalendar :options="calendarOptions" />
    </div>

    <!-- Modal Agregar/Editar Cita -->
    <div v-if="showCitaModal" class="modal" @click.self="closeCitaModal">
      <div class="modal-content">
        <span class="close" @click="closeCitaModal">&times;</span>
        <h2>{{ modalTitle }}</h2>
        <form @submit.prevent="guardarCita">
          <div class="form-group">
            <label for="fecha">Fecha:</label>
            <input type="date" v-model="formData.fecha" required />
          </div>
          <div class="form-group">
            <label for="hora">Hora:</label>
            <input type="time" v-model="formData.hora" required />
          </div>
          <div class="form-group">
            <label for="paciente">Paciente:</label>
            <input
                type="text"
                v-model="formData.paciente"
                @input="validarPaciente"
                :class="{ invalid: pacienteInvalido, valid: pacienteValido }"
                required
            />
            <div v-if="validandoPaciente" class="validation-message loading">
              <i class="fas fa-spinner fa-spin"></i>
              <span>Verificando paciente...</span>
            </div>
            <div v-if="pacienteInvalido" class="validation-message error">
              <i class="fas fa-exclamation-circle"></i>
              <span>Este paciente no está registrado en el sistema.</span>
            </div>
            <div v-if="pacienteValido" class="validation-message success">
              <i class="fas fa-check-circle"></i>
              <span>Paciente registrado</span>
            </div>
          </div>
          <div class="form-group">
            <label for="tipo">Tipo de Cita:</label>
            <select v-model="formData.tipo" required>
              <option value="">Seleccione el tipo</option>
              <option value="Prueba de Antígenos">Prueba de Antígenos</option>
              <option value="Seguimiento de Tratamiento">Seguimiento de Tratamiento</option>
            </select>
          </div>
          <div class="form-group">
            <label for="notas">Notas adicionales:</label>
            <textarea v-model="formData.notas" rows="3"></textarea>
          </div>
          <div class="form-buttons">
            <button type="submit" id="guardarCita">Guardar Cita</button>
            <button type="button" id="cancelarCita" @click="closeCitaModal">
              Cancelar
            </button>
          </div>
        </form>
      </div>
    </div>

    <!-- Modal Info Cita -->
    <div v-if="showInfoModal" class="modal" @click.self="closeInfoModal">
      <div class="modal-content">
        <span class="close" @click="closeInfoModal">&times;</span>
        <h2>Información de la Cita</h2>
        <div id="citaInfo">
          <p><strong>Paciente:</strong> {{ citaActual.paciente }}</p>
          <p><strong>Fecha y Hora:</strong> {{ citaActual.fechaHora }}</p>
          <p><strong>Tipo:</strong> {{ citaActual.tipo }}</p>
          <p><strong>Notas:</strong> {{ citaActual.notas || 'No hay notas adicionales' }}</p>
        </div>
        <div class="action-buttons">
          <button id="comenzarCitaBtn" @click="comenzarCitaModal">
            Comenzar Cita
          </button>
          <button id="reagendarBtn" @click="reagendarCita">Reagendar</button>
          <button id="editarBtn" @click="editarCita">Modificar</button>
          <button id="eliminarBtn" @click="eliminarCita">Eliminar</button>
          <button id="cerrarInfoBtn" @click="closeInfoModal">Cerrar</button>
        </div>
      </div>
    </div>

    <!-- Modal Paciente No Registrado -->
    <div v-if="showPacienteNoRegistradoModal" class="modal" @click.self="closePacienteNoRegistradoModal">
      <div class="modal-content">
        <span class="close" @click="closePacienteNoRegistradoModal">&times;</span>
        <h2>Paciente No Registrado</h2>
        <div class="alert-message">
          <i class="fas fa-exclamation-triangle"></i>
          <p>El paciente <strong>{{ formData.paciente }}</strong> no se encuentra registrado en el sistema.</p>
          <p>Por favor, regístrelo primero en la sección de <strong>Administrar Paciente</strong> antes de agendar una cita.</p>
        </div>
        <div class="action-buttons">
          <button @click="irAgregarPaciente">Agregar Paciente</button>
          <button @click="closePacienteNoRegistradoModal">Cerrar</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
//import { ref, computed, onMounted, onBeforeUnmount } from 'vue';
import '@/calendar.css'
import {ref, computed, onMounted} from 'vue';
import { useRouter } from 'vue-router';
import FullCalendar from '@fullcalendar/vue3';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import esLocale from '@fullcalendar/core/locales/es';

const router = useRouter();

// Estados
const showCitaModal = ref(false);
const showInfoModal = ref(false);
const showPacienteNoRegistradoModal = ref(false);
const modalTitle = ref('Agendar Nueva Cita');
const currentEventId = ref<string | null>(null);
const currentEvent = ref<any>(null);

// Validación de paciente
const validandoPaciente = ref(false);
const pacienteInvalido = ref(false);
const pacienteValido = ref(false);

// Lista de pacientes registrados (simulado)
const pacientesRegistrados = ['Citli', 'Brayan Cruz', 'Alvaro', 'María González', 'Juan Pérez'];

// Formulario
const formData = ref({
  fecha: '',
  hora: '09:00',
  paciente: '',
  tipo: '',
  notas: ''
});

// Info cita actual
const citaActual = ref({
  paciente: '',
  fechaHora: '',
  tipo: '',
  notas: ''
});

// Eventos del calendario
const eventos = ref([
  {
    id: '1',
    title: 'Citli - Prueba de Antígenos',
    start: '2025-01-25T10:00:00',
    extendedProps: {
      paciente: 'Citli',
      tipo: 'Prueba de Antígenos',
      notas: 'Primera visita'
    }
  },
  {
    id: '2',
    title: 'Brayan Cruz - Seguimiento de Tratamiento',
    start: '2025-01-25T10:30:00',
    backgroundColor: '#b850c7',
    borderColor: '#b850c7',
    extendedProps: {
      paciente: 'Brayan Cruz',
      tipo: 'Seguimiento de Tratamiento',
      notas: 'Control mensual'
    }
  }
]);

// Citas de hoy
const citasDeHoy = computed(() => {
  const hoy = new Date().toISOString().split('T')[0];
  return eventos.value
      .filter(e => e.start.startsWith(hoy))
      .map(e => ({
        id: e.id,
        hora: new Date(e.start).toLocaleTimeString('es-ES', { hour: '2-digit', minute: '2-digit' }),
        paciente: e.extendedProps.paciente,
        tipo: e.extendedProps.tipo
      }));
});

// Opciones del calendario
const calendarOptions = ref({
  plugins: [dayGridPlugin, timeGridPlugin, interactionPlugin],
  initialView: 'dayGridMonth',
  locale: esLocale,
  height: 'auto',
  headerToolbar: {
    left: 'prev,next today',
    center: 'title',
    right: 'dayGridMonth,timeGridWeek,timeGridDay'
  },
  selectable: true,
  editable: true,
  events: eventos,
  dateClick: handleDateClick,
  eventClick: handleEventClick
});

// Funciones
function handleDateClick(info: any) {
  modalTitle.value = 'Agendar Nueva Cita';
  formData.value = {
    fecha: info.dateStr,
    hora: '09:00',
    paciente: '',
    tipo: '',
    notas: ''
  };
  currentEventId.value = null;
  resetValidacion();
  showCitaModal.value = true;
}

function handleEventClick(info: any) {
  currentEvent.value = info.event;
  const start = info.event.start;
  citaActual.value = {
    paciente: info.event.extendedProps.paciente || info.event.title,
    fechaHora: `${start.toLocaleDateString('es-ES')} ${start.toLocaleTimeString('es-ES', { hour: '2-digit', minute: '2-digit' })}`,
    tipo: info.event.extendedProps.tipo || 'Consulta',
    notas: info.event.extendedProps.notas || ''
  };
  showInfoModal.value = true;
}

let timeoutId: any = null;
async function validarPaciente() {
  const paciente = formData.value.paciente.trim();

  resetValidacion();

  if (paciente.length < 3) return;

  validandoPaciente.value = true;

  if (timeoutId) clearTimeout(timeoutId);

  timeoutId = setTimeout(async () => {
    const existe = pacientesRegistrados.includes(paciente);
    validandoPaciente.value = false;

    if (existe) {
      pacienteValido.value = true;
    } else {
      pacienteInvalido.value = true;
    }
  }, 800);
}

function resetValidacion() {
  validandoPaciente.value = false;
  pacienteInvalido.value = false;
  pacienteValido.value = false;
}

async function guardarCita() {
  const paciente = formData.value.paciente.trim();

  if (!paciente || !formData.value.fecha || !formData.value.hora || !formData.value.tipo) {
    alert('Por favor complete todos los campos obligatorios');
    return;
  }

  const existe = pacientesRegistrados.includes(paciente);

  if (!existe) {
    showPacienteNoRegistradoModal.value = true;
    return;
  }

  const startDateTime = `${formData.value.fecha}T${formData.value.hora}:00`;
  const nuevoEvento = {
    id: currentEventId.value || Date.now().toString(),
    title: `${paciente} - ${formData.value.tipo}`,
    start: startDateTime,
    extendedProps: {
      paciente: paciente,
      tipo: formData.value.tipo,
      notas: formData.value.notas
    }
  };

  if (currentEventId.value) {
    const index = eventos.value.findIndex(e => e.id === currentEventId.value);
    if (index !== -1) {
      eventos.value[index] = nuevoEvento;
    }
  } else {
    eventos.value.push(nuevoEvento);
  }

  closeCitaModal();
  alert('Cita guardada exitosamente');
}

function reagendarCita() {
  if (!currentEvent.value) return;

  closeInfoModal();
  modalTitle.value = 'Reagendar Cita';
  const start = currentEvent.value.start;
  formData.value = {
    fecha: start.toISOString().split('T')[0],
    hora: start.toTimeString().substring(0, 5),
    paciente: currentEvent.value.extendedProps.paciente || '',
    tipo: currentEvent.value.extendedProps.tipo || '',
    notas: currentEvent.value.extendedProps.notas || ''
  };
  currentEventId.value = currentEvent.value.id;
  resetValidacion();
  showCitaModal.value = true;
}

function editarCita() {
  reagendarCita();
  modalTitle.value = 'Modificar Cita';
}

function eliminarCita() {
  if (!currentEvent.value) return;

  if (confirm('¿Está seguro de que desea eliminar esta cita?')) {
    const index = eventos.value.findIndex(e => e.id === currentEvent.value.id);
    if (index !== -1) {
      eventos.value.splice(index, 1);
    }
    closeInfoModal();
    alert('Cita eliminada exitosamente');
  }
}

function comenzarCitaModal() {
  const tipo = citaActual.value.tipo.toLowerCase();
  const paciente = citaActual.value.paciente;

  if (tipo.includes('antígenos')) {
    router.push({ name: 'PruebasAlergias', query: { paciente } });
  } else if (tipo.includes('seguimiento')) {
    router.push({ name: 'SeguimientoTratamiento', query: { paciente } });
  } else {
    alert('Redirección no configurada para este tipo de cita');
  }
}

function iniciarCita(tipo: string) {
  if (tipo === 'Prueba de Antígenos') {
    router.push({ name: 'PruebasAlergias' });
  } else if (tipo === 'Seguimiento de Tratamiento') {
    router.push({ name: 'SeguimientoTratamiento' });
  }
}

function irAgregarPaciente() {
  router.push({
    name: 'AgregarPaciente',
    query: {
      nombre: formData.value.paciente,
      fecha_nacimiento: formData.value.fecha
    }
  });
}

function closeCitaModal() {
  showCitaModal.value = false;
}

function closeInfoModal() {
  showInfoModal.value = false;
}

function closePacienteNoRegistradoModal() {
  showPacienteNoRegistradoModal.value = false;
}

// Verificar parámetros de URL al montar
onMounted(() => {
  const urlParams = new URLSearchParams(window.location.search);
  const paciente = urlParams.get('paciente');

  if (paciente) {
    formData.value.paciente = paciente;
    showCitaModal.value = true;
  }
});
</script>

<style scoped>
/* Los estilos están en src/assets/css/calendar-styles.css */
.start-btn {
  background-color: var(--success-color);
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  font-weight: 600;
  transition: all 0.3s ease;
  width: 100%;
  margin-top: 10px;
}

.start-btn:hover {
  background-color: #27ae60;
}
</style>