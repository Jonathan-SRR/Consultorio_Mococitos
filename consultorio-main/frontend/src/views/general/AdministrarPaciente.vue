<template>
  <main class="content">
    <div class="card-paciente">
      <div class="card-header">
        <h2>Datos del Paciente</h2>
        <div class="patient-id-badge">
          <i class="fas fa-id-card"></i> ID: {{ paciente.id ?? '—' }}
        </div>
      </div>

      <div class="form-grid" v-if="!loading && !error">
        <div class="form-group">
          <label>Nombre Completo</label>
          <p>{{ fullName }}</p>
        </div>
        <div class="form-group">
          <label>Fecha de Nacimiento</label>
          <p>{{ fechaFormateada }}</p>
        </div>
        <div class="form-group">
          <label>Edad</label>
          <p>{{ edad !== null ? edad + ' años' : '—' }}</p>
        </div>
        <div class="form-group">
          <label>Sexo</label>
          <p>{{ sexoFormateado }}</p>
        </div>
        <div class="form-group">
          <label>Nombre del Tutor</label>
          <p>{{ tutorNombre }}</p>
        </div>
        <div class="form-group">
          <label>Correo del Tutor</label>
          <p>{{ tutorCorreo }}</p>
        </div>
        <div class="form-group">
          <label>Teléfono del Tutor</label>
          <p>{{ tutorTelefono }}</p>
        </div>
        <div class="form-group">
          <label>Dirección Completa</label>
          <p>{{ direccionCompleta }}</p>
        </div>
        <div class="form-group">
          <label>Estado</label>
          <p>
            <span class="status-badge" :class="paciente.estatus ? 'active' : 'inactive'">
              {{ paciente.estatus ? 'Activo' : 'Inactivo' }}
            </span>
          </p>
        </div>
      </div>

      <div v-if="loading" style="padding:16px; text-align:center;">Cargando paciente...</div>
      <div v-if="error" style="padding:16px; text-align:center; color:crimson;">{{ error }}</div>
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

    <!-- Acciones del paciente -->
    <div class="card-paciente">
      <h2>Acciones</h2>
      <div class="form-actions">
            <button class="btn btn-primary" @click="editarPaciente">
          <i class="fas fa-edit"></i> Editar Paciente
        </button>
            <button class="btn btn-primary" @click="agendarCita">
              <i class="fas fa-calendar-plus"></i> Agendar Cita
            </button>
        <button class="btn btn-danger" @click="eliminarPaciente" v-if="paciente.estatus">
          <i class="fas fa-trash"></i> Desactivar Paciente
        </button>
        <button class="btn btn-success" @click="activarPaciente" v-else>
          <i class="fas fa-check"></i> Activar Paciente
        </button>
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import api from '@/plugins/api';
import { fetchPacienteWithTutor, normalizeRawPaciente as normalizePacienteShared } from '@/plugins/pacienteService';

const route = useRoute();
const router = useRouter();

const paciente = ref<any>({
  id: null,
  nombre: '',
  apellidoPaterno: '',
  apellidoMaterno: '',
  fechaNacimiento: null,
  sexo: '',
  estatus: true
});
const tutor = ref<any | null>(null);
const direccion = ref<any | null>(null);

const loading = ref(false);
const error = ref<string | null>(null);

// Obtener ID desde params, query o history state
const getIdFromRoute = () => {
  console.log('Route params:', route.params);
  console.log('Route query:', route.query);

  const fromParams = route.params.id || route.query.id;
  if (fromParams) {
    const idNumber = Number(fromParams);
    return isNaN(idNumber) ? null : idNumber;
  }

  // Fallback: check window.history.state for a possible passed patient or params (router may store extra state)
  try {
    const historyState: any = (typeof window !== 'undefined' && window.history && window.history.state) ? window.history.state : null;
    if (historyState) {
      // Some router versions embed a current object with params
      const s = historyState.current ?? historyState;
      if (s) {
        const idFromHistory = s.params?.id ?? s.id ?? s.paciente?.id ?? s.paciente?.id_paciente;
        if (idFromHistory) {
          const idNumber2 = Number(idFromHistory);
          return isNaN(idNumber2) ? null : idNumber2;
        }
      }
    }
  } catch (e) {
    console.warn('No se pudo leer history.state:', e);
  }

  return null;
};

const normalizeRawPaciente = normalizePacienteShared;

const cargarPaciente = async (id: number | null) => {
  if (!id) {
    error.value = 'ID de paciente no proporcionado';
    return;
  }

  console.log('Cargando paciente con ID:', id);

  loading.value = true;
  error.value = null;
  try {
    const { paciente: p, tutor: t, direccion: d } = await fetchPacienteWithTutor(Number(id));
    if (!p) {
      error.value = 'Paciente no encontrado';
      return;
    }
    paciente.value = normalizeRawPaciente(p);
    tutor.value = t ?? null;
    direccion.value = d ?? null;
  } catch (err: any) {
    console.error('Error cargando paciente:', err);
    error.value = err?.response?.data?.message || err?.message || 'No se pudo cargar el paciente';
  } finally {
    loading.value = false;
  }
};

// Computed - nombre completo
const fullName = computed(() => {
  const p = paciente.value;
  return `${p.nombre ?? ''} ${p.apellidoPaterno ?? ''} ${p.apellidoMaterno ?? ''}`
      .trim() || '—';
});

// Sexo formateado
const sexoFormateado = computed(() => {
  const sexo = paciente.value.sexo;
  return sexo === 'M' ? 'Masculino' : sexo === 'F' ? 'Femenino' : '—';
});

// Fecha formateada dd/mm/YYYY
const fechaFormateada = computed(() => {
  const f = paciente.value.fechaNacimiento;
  if (!f) return '—';
  try {
    const s = String(f).substr(0, 10);
    const parts = s.split('-');
    if (parts.length === 3) return `${parts[2]}/${parts[1]}/${parts[0]}`;
    return s;
  } catch { return String(f); }
});

// Edad
const edad = computed(() => {
  const f = paciente.value.fechaNacimiento;
  if (!f) return null;

  try {
    const [y, m, d] = String(f).substr(0, 10).split('-').map(Number);
    const dob = new Date(y, m - 1, d);
    const diff = Date.now() - dob.getTime();
    const ageDt = new Date(diff);
    return Math.abs(ageDt.getUTCFullYear() - 1970);
  } catch {
    return null;
  }
});

// Tutor data
const tutorNombre = computed(() => {
  if (!tutor.value) return '—';
  return `${tutor.value.nombre_tutor ?? tutor.value.nombre} ${tutor.value.apellido_paterno ?? tutor.value.apellidoPaterno} ${tutor.value.apellido_materno ?? tutor.value.apellidoMaterno}`.trim();
});
const tutorCorreo = computed(() => tutor.value?.correo ?? '—');
const tutorTelefono = computed(() => tutor.value?.telefono ?? '—');

// Dirección completa
const direccionCompleta = computed(() => {
  if (!direccion.value) return '—';

  const parts: string[] = [];

  if (direccion.value.calle) {
    parts.push(
        direccion.value.calle +
        (direccion.value.numero_ext ? ` #${direccion.value.numero_ext}` : '')
    );
  }

  if (direccion.value.colonia) parts.push(direccion.value.colonia);
  if (direccion.value.estado) parts.push(direccion.value.estado);
  if (direccion.value.codigo_postal) parts.push(String(direccion.value.codigo_postal));

  return parts.join(', ') || '—';
});

const editarPaciente = () => {
  if (!paciente.value.id) {
    alert('No se puede editar: ID del paciente no disponible');
    return;
  }
  router.push({
    name: 'AgregarPaciente',
    query: { id: paciente.value.id },
    state: { paciente: paciente.value, tutor: tutor.value, direccion: direccion.value }
  });
};

const agendarCita = () => {
  if (!paciente.value.id) {
    alert('No se puede agendar cita: ID del paciente no disponible');
    return;
  }
  router.push({
    name: 'Calendario',
    query: { paciente: fullName.value, pacienteId: String(paciente.value.id) }
  });
};

const eliminarPaciente = async () => {
  if (!paciente.value.id) {
    alert('No se puede eliminar: ID del paciente no disponible');
    return;
  }

  if (confirm('¿Está seguro que desea desactivar este paciente?')) {
    try {
      console.log('Eliminando paciente con ID:', paciente.value.id);
      await api.delete(`/api/pacientes/${paciente.value.id}`);
      paciente.value.estatus = false;
      alert('Paciente desactivado correctamente');
    } catch (error: any) {
      console.error('Error desactivando paciente:', error);
      const errorMsg = error?.response?.data?.message || error?.message || 'Error al desactivar el paciente';
      alert('Error al desactivar el paciente: ' + errorMsg);
    }
  }
};

const activarPaciente = async () => {
  if (!paciente.value.id) {
    alert('No se puede activar: ID del paciente no disponible');
    return;
  }

  try {
    // Crear objeto con los datos actualizados
    const pacienteActualizado = {
      id_paciente: paciente.value.id,
      nombre_paciente: paciente.value.nombre,
      apellido_paterno: paciente.value.apellidoPaterno,
      apellido_materno: paciente.value.apellidoMaterno,
      fecha_nacimiento: paciente.value.fechaNacimiento,
      sexo: paciente.value.sexo,
      estatus: true
    };

    console.log('Activando paciente con datos:', pacienteActualizado);
    await api.put(`/api/pacientes/${paciente.value.id}`, pacienteActualizado);
    paciente.value.estatus = true;
    alert('Paciente activado correctamente');
  } catch (error: any) {
    console.error('Error activando paciente:', error);
    const errorMsg = error?.response?.data?.message || error?.message || 'Error al activar el paciente';
    alert('Error al activar el paciente: ' + errorMsg);
  }
};

onMounted(() => {
  const id = getIdFromRoute();
  console.log('ID obtenido de la ruta:', id);
  // First, check if there's patient data in history.state to prefill the UI while API loads
  try {
    const hs: any = (typeof window !== 'undefined' && window.history && window.history.state) ? window.history.state : null;
    if (hs && hs.paciente) {
      // Normalize and set patient data
      paciente.value = normalizeRawPaciente(hs.paciente);
      // If the history contains tutor/direccion, set them also
      if (hs.tutor) tutor.value = hs.tutor;
      if (hs.direccion) direccion.value = hs.direccion;
    }
  } catch (e) {
    console.warn('No se pudo leer patient desde history.state:', e);
  }

  if (id) {
    // Always attempt to load latest from API
    cargarPaciente(id);
  } else {
    // If no ID provided, show a friendly message
    error.value = 'No se proporcionó ID de paciente en la ruta';
    console.error('No se pudo obtener ID de la ruta');
  }
});
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
  padding-bottom: 10px;
}

@media (max-width: 1190px) {
  .card-paciente .form-actions {
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
.btn-historial { background-color: #17a2b8; }
.btn-historial:hover { background-color: #138496; }

.btn-seguimiento { background-color: #28a745; }
.btn-seguimiento:hover { background-color: #218838; }

.btn-pruebas { background-color: #fcac17; color: white; }
.btn-pruebas:hover { background-color: #e0a800; }

.btn-vacunas { background-color: #6f42c1; }
.btn-vacunas:hover { background-color: #5a359c; }

.btn-reporte { background-color: #e74c3c; }
.btn-reporte:hover { background-color: #c0392b; }

.btn-primary { background-color: var(--primary-color); }
.btn-primary:hover { background-color: #2c8bb8; }

.btn-danger { background-color: #dc3545; }
.btn-danger:hover { background-color: #c82333; }

.btn-success { background-color: #28a745; }
.btn-success:hover { background-color: #218838; }

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

.status-badge {
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 600;
  text-transform: uppercase;
}
.status-badge.active {
  background-color: #d4edda;
  color: #155724;
}
.status-badge.inactive {
  background-color: #f8d7da;
  color: #721c24;
}
</style>