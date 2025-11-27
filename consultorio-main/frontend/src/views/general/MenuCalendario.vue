<template>
  <div>
    <!-- Panel lateral citas de hoy -->
    <div class="today-appointments">
      <h2>Citas de Hoy</h2>
      <div class="appointments-list">
        <p v-if="citasDeHoy.length === 0" style="padding: 10px; opacity: 0.7; text-align: center;">
          No hay citas para hoy.
        </p>

        <div v-for="cita in citasDeHoy" :key="cita.id" class="appointment-card">
          <span class="appointment-time">{{ cita.hora }}</span>
          <span class="appointment-patient">Paciente: {{ cita.paciente }}</span>
          <span class="appointment-type">{{ cita.tipo }}</span>
          <button class="comenzar-btn" @click="iniciarCita(cita)">Comenzar</button>
        </div>
      </div>
    </div>

    <!-- Calendario -->
    <div id="calendar-container">
      <FullCalendar ref="calendarRef" :options="calendarOptions" />
    </div>

    <!-- ---------------------- MODAL AGREGAR / EDITAR ---------------------- -->
    <div v-if="showCitaModal" class="modal" @click.self="closeCitaModal">
      <div class="modal-content">
        <span class="close" @click="closeCitaModal">&times;</span>
        <h2>{{ modalTitle }}</h2>
        <form @submit.prevent="guardarCita">
          <div class="form-group">
            <label>Fecha:</label>
            <input type="date" v-model="formData.fecha" required />
          </div>
          <div class="form-group">
            <label>Hora:</label>
            <input type="time" v-model="formData.hora" required />
          </div>
          <div class="form-group">
            <label>Paciente (ID):</label>
            <input
                type="text"
                v-model="formData.paciente"
                @input="validarPaciente"
                :class="{ invalid: pacienteInvalido, valid: pacienteValido }"
                required
                placeholder="Ej: 1"
            />
            <div v-if="validandoPaciente" class="validation-message loading">
              <i class="fas fa-spinner fa-spin"></i> Verificando...
            </div>
            <div v-if="pacienteInvalido" class="validation-message error">
              <i class="fas fa-exclamation-circle"></i> Paciente no encontrado.
            </div>
            <div v-if="pacienteValido" class="validation-message success">
              <i class="fas fa-check-circle"></i> Paciente válido: {{ pacienteBuscadoNombre || formData.paciente }}
            </div>
          </div>
          <div class="form-group">
            <label>Tipo de Cita:</label>
            <select v-model="formData.tipo" required>
              <option value="">Seleccione el tipo</option>
              <option value="Prueba de Antígenos">Prueba de Antígenos</option>
              <option value="Seguimiento de Tratamiento">Seguimiento de Tratamiento</option>
              <option value="Consulta Médica">Consulta Médica</option>
            </select>
          </div>
          <div class="form-group">
            <label>Notas:</label>
            <textarea v-model="formData.notas" rows="3"></textarea>
          </div>
          <div class="form-buttons">
            <button type="submit" class="start-btn">Guardar Cita</button>
            <button type="button" class="start-btn" style="background-color: #e74c3c;" @click="closeCitaModal">Cancelar</button>
          </div>
        </form>
      </div>
    </div>

    <!-- ------------------------ MODAL INFO ------------------------ -->
    <div v-if="showInfoModal" class="modal" @click.self="closeInfoModal">
      <div class="modal-content">
        <span class="close" @click="closeInfoModal">&times;</span>
        <h2>Información de la Cita</h2>
        <div id="citaInfo">
          <p><strong>Paciente:</strong> {{ citaActual.paciente }}</p>
          <p><strong>Fecha y Hora:</strong> {{ citaActual.fechaHora }}</p>
          <p><strong>Tipo:</strong> {{ citaActual.tipo }}</p>
          <p><strong>Notas:</strong> {{ citaActual.notas || 'Sin notas' }}</p>
        </div>
        <div class="action-buttons">
          <button class="start-btn" @click="comenzarCitaModal">Comenzar Cita</button>
          <button class="start-btn" style="background-color: #f39c12;" @click="reagendarCita">Reagendar</button>
          <button class="start-btn" style="background-color: #c0392b;" @click="eliminarCita">Eliminar</button>
          <button class="start-btn" style="background-color: #7f8c8d;" @click="closeInfoModal">Cerrar</button>
        </div>
      </div>
    </div>

    <!-- ------------------ MODAL PACIENTE NO REGISTRADO ------------------ -->
    <div v-if="showPacienteNoRegistradoModal" class="modal" @click.self="closePacienteNoRegistradoModal">
      <div class="modal-content">
        <span class="close" @click="closePacienteNoRegistradoModal">&times;</span>
        <h2>Paciente No Registrado</h2>
        <div class="alert-message">
          <p>El paciente con ese ID no existe en la base de datos.</p>
        </div>
        <div class="action-buttons">
          <button @click="irAgregarPaciente" class="start-btn">Agregar Paciente</button>
          <button @click="closePacienteNoRegistradoModal" class="start-btn" style="background-color: #7f8c8d;">Cerrar</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/plugins/api';
import { fetchPacienteWithTutor } from '@/plugins/pacienteService';
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
const currentEvent = ref<any>(null);
const validandoPaciente = ref(false);
const pacienteInvalido = ref(false);
const pacienteValido = ref(false);

const formData = ref({
  id: null as string | null,
  fecha: '',
  hora: '09:00',
  paciente: '',
  tipo: '',
  notas: '',
  estatus: '1'
});

const citaActual = ref({
  paciente: '',
  fechaHora: '',
  tipo: '',
  notas: '',
  estatus: ''
});

const pacienteBuscadoNombre = computed(() => {
  const p = pacienteBuscado.value;
  if (!p) return '';
  const nombre = p.nombre ?? p.nombre_paciente ?? '';
  const apellido = p.apellidoPaterno ?? p.apellido_paterno ?? '';
  return (nombre + ' ' + apellido).trim();
});

// Calendario
const eventos = ref<any[]>([]);
const calendarRef = ref<any | null>(null);
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
  // Let FullCalendar call the backend to fetch events when needed.
  events: (_fetchInfo: any, successCallback: any, failureCallback: any) => {
    api.get(`/api/citas/calendar`)
      .then(res => {
        const datos = res.data;
        const eventosFormateados = datos.map((cita: any) => {
          let fechaISO = cita.fecha;
          if (fechaISO.includes(' ')) fechaISO = fechaISO.replace(' ', 'T');
          const startDate = new Date(fechaISO);
          return {
            id: String(cita.idCita),
            title: `Paciente ${cita.idPaciente} - ${cita.tipoCita}`,
            start: startDate,
            extendedProps: {
              paciente: cita.idPaciente,
              tipo: cita.tipoCita,
              notas: cita.notas,
              estatus: cita.estatus
            }
          };
        });
        successCallback(eventosFormateados);
      })
      .catch(err => {
        console.error('Error fetching calendar events:', err);
        failureCallback(err);
      });
  },
  dateClick: (info: any) => {
    modalTitle.value = 'Agendar Nueva Cita';
    formData.value = {
      id: null,
      fecha: info.dateStr,
      hora: '09:00',
      paciente: '',
      tipo: '',
      notas: '',
      estatus: '1'
    };
    resetValidacion();
    showCitaModal.value = true;
  },
  eventClick: (info: any) => {
    currentEvent.value = info.event;
    const start = info.event.start;
    citaActual.value = {
      paciente: info.event.extendedProps.paciente,
      fechaHora: `${start.toLocaleDateString('es-ES')} ${start.toLocaleTimeString('es-ES')}`,
      tipo: info.event.extendedProps.tipo,
      notas: info.event.extendedProps.notas,
      estatus: info.event.extendedProps.estatus
    };
    showInfoModal.value = true;
  }
});

// Citas de hoy
const citasDeHoy = computed(() => {
  const hoy = new Date();
  const yyyy = hoy.getFullYear();
  const mm = String(hoy.getMonth() + 1).padStart(2, '0');
  const dd = String(hoy.getDate()).padStart(2, '0');
  const hoyString = `${yyyy}-${mm}-${dd}`;
  return eventos.value
      .filter(ev => {
        const start = ev.start instanceof Date ? ev.start : new Date(ev.start);
        const y = start.getFullYear();
        const m = String(start.getMonth() + 1).padStart(2, '0');
        const d = String(start.getDate()).padStart(2, '0');
        const dateStr = `${y}-${m}-${d}`;
        return dateStr === hoyString;
      })
      .map(ev => {
        const fechaObj = ev.start instanceof Date ? ev.start : new Date(ev.start);
        return {
          id: ev.id,
          hora: fechaObj.toLocaleTimeString('es-ES', { hour: '2-digit', minute: '2-digit' }),
          paciente: ev.extendedProps.paciente,
          tipo: ev.extendedProps.tipo,
          estatus: ev.extendedProps.estatus
        };
      });
});

// Funciones modales
function closeCitaModal() { showCitaModal.value = false; }
function closeInfoModal() { showInfoModal.value = false; }
function closePacienteNoRegistradoModal() { showPacienteNoRegistradoModal.value = false; }
function resetValidacion() {
  validandoPaciente.value = false;
  pacienteInvalido.value = false;
  pacienteValido.value = false;
}

// Cargar eventos
async function cargarEventos() {
  try {
    const res = await api.get(`/api/citas/calendar`);
    const datos = res.data;

    const eventosFormateados = datos.map((cita: any) => {
      let fechaISO = cita.fecha;
      if (fechaISO.includes(' ')) fechaISO = fechaISO.replace(' ', 'T');
      const startDate = new Date(fechaISO);
      return {
        id: String(cita.idCita),
        title: `Paciente ${cita.idPaciente} - ${cita.tipoCita}`,
        start: startDate,
        extendedProps: {
          paciente: cita.idPaciente,
          tipo: cita.tipoCita,
          notas: cita.notas,
          estatus: cita.estatus
        }
      };
    });

    eventos.value = eventosFormateados;
    calendarOptions.value.events = eventosFormateados;
  } catch (error) {
    console.error("Error cargando eventos:", error);
  }
}

// Validar paciente
let timeoutId: any = null;
const pacienteBuscado = ref<any | null>(null);
async function validarPaciente() {
  const paciente = formData.value.paciente.trim();
  resetValidacion();
  if (!paciente) return;

  // Only numeric ID is allowed
  if (!/^[0-9]+$/.test(paciente)) {
    pacienteInvalido.value = true;
    return;
  }

  validandoPaciente.value = true;
  if (timeoutId) clearTimeout(timeoutId);

  timeoutId = setTimeout(async () => {
    try {
      const idNumber = Number(paciente);
      const res = await fetchPacienteWithTutor(idNumber);
      const p = res?.paciente ?? null;
      validandoPaciente.value = false;
      if (p && p.id) {
        pacienteBuscado.value = p;
        pacienteValido.value = true;
      } else {
        pacienteInvalido.value = true;
      }
    } catch (e) {
      pacienteBuscado.value = null;
      pacienteInvalido.value = true;
      validandoPaciente.value = false;
    }
  }, 500);
}

// Convierte DD/MM/YYYY → YYYY-MM-DD
function convertirFecha(fechaDDMMYYYY: string) {
  if (!fechaDDMMYYYY.includes('/')) return fechaDDMMYYYY; // ya viene en formato ISO
  const [dia, mes, año] = fechaDDMMYYYY.split('/');
  return `${año}-${mes}-${dia}`;
}

async function guardarCita() {
  if (!formData.value.paciente || !formData.value.fecha || !formData.value.hora || !formData.value.tipo) {
    alert('Complete los campos obligatorios');
    return;
  }

  // Convertir fecha DD/MM/YYYY → YYYY-MM-DD
  const fechaISO = convertirFecha(formData.value.fecha);

  // *** UNIFICAR fecha + hora → LocalDateTime ***
  const fechaCompleta = `${fechaISO}T${formData.value.hora}:00`;

  const datosCita = {
    idCita: formData.value.id ? Number(formData.value.id) : null,
    paciente: { idPaciente: Number(formData.value.paciente) },
    fecha: fechaCompleta,
    tipoCita: formData.value.tipo,
    notas: formData.value.notas,
    estatus: 1
  };

  try {
    if (formData.value.id) {
      await api.put(`/api/citas/${formData.value.id}`, datosCita);
      alert('Cita actualizada exitosamente');
    } else {
      await api.post(`/api/citas`, datosCita);
      alert('Cita guardada exitosamente');
    }

    await cargarEventos();
    closeCitaModal();
  } catch (error) {
    console.error("Error guardando:", error);
    alert('Error al guardar la cita. Verifique los datos.');
  }
}


// Eliminar cita
async function eliminarCita() {
  if (!currentEvent.value) return;
  if (!confirm('¿Seguro que desea eliminar esta cita?')) return;
  try {
    await api.delete(`/api/citas/${currentEvent.value.id}`);
    await cargarEventos();
    closeInfoModal();
    alert('Cita eliminada');
  } catch (error) { console.error(error); }
}

// Reagendar / editar cita
function reagendarCita() {
  if (!currentEvent.value) return;
  const start = currentEvent.value.start;
  formData.value = {
    id: currentEvent.value.id,
    fecha: start.toISOString().split('T')[0],
    hora: start.toTimeString().substring(0, 5),
    paciente: currentEvent.value.extendedProps.paciente,
    tipo: currentEvent.value.extendedProps.tipo,
    notas: currentEvent.value.extendedProps.notas,
    estatus: currentEvent.value.extendedProps.estatus
  };
  closeInfoModal();
  modalTitle.value = 'Reagendar Cita';
  pacienteValido.value = true;
  showCitaModal.value = true;
}

// Funciones auxiliares
// Function removed: reemplazado por reagendarCita ya que no se usa directamente
function comenzarCitaModal() { alert("Iniciando cita..."); }
function iniciarCita(cita: any) { alert("Iniciando cita: " + cita.tipo); }
function irAgregarPaciente() { router.push({ name: 'AgregarPaciente', query: { nombre: formData.value.paciente } }); }

onMounted(() => { cargarEventos(); });
</script>

<style scoped>
.validation-message { display: flex; align-items: center; font-size: 0.85rem; margin-top: 5px; }
.validation-message.loading { color: #555; }
.validation-message.success { color: #27ae60; }
.validation-message.error { color: #e74c3c; }
.validation-message i { margin-right: 5px; }
input.valid { border-color: #27ae60; }
input.invalid { border-color: #e74c3c; }
.start-btn { background-color: var(--success-color); color: white; border: none; padding: 8px 16px; border-radius: 4px; cursor: pointer; width: 100%; margin-top: 10px; }
.modal { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); display: flex; justify-content: center; align-items: center; z-index: 9999; }
.modal-content { background: #fff; padding: 20px; border-radius: 8px; width: 400px; max-width: 90%; position: relative; }
.close { position: absolute; top: 10px; right: 15px; font-size: 24px; cursor: pointer; }
.action-buttons { display: flex; flex-direction: column; gap: 10px; margin-top: 15px; }
.today-appointments { margin-bottom: 20px; }
.appointment-card { background: #f7f7f7; padding: 10px; border-radius: 5px; margin-bottom: 5px; display: flex; flex-direction: column; gap: 4px; }
.appointment-time { font-weight: bold; }
</style>

