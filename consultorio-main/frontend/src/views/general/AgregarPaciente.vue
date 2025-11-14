<template>
  <div>
    <form id="form-agregar-paciente" class="formulario" @submit.prevent="guardarPaciente">

      <!-- PANEL: Datos del Paciente -->
      <div class="panel">
        <h2>Datos del Paciente</h2>
        <div class="form-grid">
          <div class="form-group">
            <label for="nombre">Nombre</label>
            <input type="text" id="nombre" v-model="paciente.nombre" required>
          </div>

          <div class="form-group">
            <label for="apellido_paterno">Apellido Paterno</label>
            <input type="text" id="apellido_paterno" v-model="paciente.apellidoPaterno" required>
          </div>

          <div class="form-group">
            <label for="apellido_materno">Apellido Materno</label>
            <input type="text" id="apellido_materno" v-model="paciente.apellidoMaterno">
          </div>

          <div class="form-group">
            <label for="fecha_nacimiento">Fecha de Nacimiento</label>
            <input type="date" id="fecha_nacimiento" v-model="paciente.fechaNacimiento" required>
          </div>

          <div class="form-group">
            <label for="sexo">Sexo</label>
            <select id="sexo" v-model="paciente.sexo" required>
              <option value="">Seleccione</option>
              <option value="M">Masculino</option>
              <option value="F">Femenino</option>
            </select>
          </div>
        </div>
      </div>

      <!-- PANEL: Datos del Tutor -->
      <div class="panel">
        <h2>Datos del Tutor</h2>
        <div class="form-grid">
          <div class="form-group">
            <label for="nombre_tutor">Nombre</label>
            <input type="text" id="nombre_tutor" v-model="tutor.nombre" required>
          </div>

          <div class="form-group">
            <label for="apellido_paterno_tutor">Apellido Paterno</label>
            <input type="text" id="apellido_paterno_tutor" v-model="tutor.apellidoPaterno" required>
          </div>

          <div class="form-group">
            <label for="apellido_materno_tutor">Apellido Materno</label>
            <input type="text" id="apellido_materno_tutor" v-model="tutor.apellidoMaterno">
          </div>

          <div class="form-group full-width">
            <label for="correo_tutor">Correo (tutor)</label>
            <input type="email" id="correo_tutor" v-model="tutor.correo" required>
          </div>

          <div class="form-group full-width">
            <label for="telefono_tutor">Teléfono (tutor)</label>
            <input type="tel" id="telefono_tutor" v-model="tutor.telefono" required>
          </div>
        </div>
      </div>

      <!-- PANEL: Dirección -->
      <div class="panel">
        <h2>Dirección</h2>
        <div class="form-grid">
          <div class="form-group">
            <label for="calle">Calle</label>
            <input type="text" id="calle" v-model="direccion.calle" required>
          </div>

          <div class="form-group">
            <label for="numero_ext">Número Ext</label>
            <input type="text" id="numero_ext" v-model="direccion.numeroExt" required>
          </div>

          <div class="form-group">
            <label for="colonia">Colonia</label>
            <input type="text" id="colonia" v-model="direccion.colonia" required>
          </div>

          <div class="form-group">
            <label for="ciudad">Ciudad</label>
            <input type="text" id="ciudad" v-model="direccion.ciudad" required>
          </div>

          <div class="form-group">
            <label for="estado">Estado</label>
            <input type="text" id="estado" v-model="direccion.estado" required>
          </div>

          <div class="form-group">
            <label for="codigo_postal">Código Postal</label>
            <input type="text" id="codigo_postal" v-model="direccion.codigoPostal" required>
          </div>
        </div>
      </div>

      <!-- Botones -->
      <div class="panel" style="padding-bottom:16px;">
        <div class="form-actions">
          <button type="submit" class="btn btn-primary">
            <i class="fas fa-save"></i> Guardar
          </button>
          <button type="button" class="btn btn-danger btn-cancelar" @click="cancelar">
            <i class="fas fa-times"></i> Cancelar
          </button>
        </div>
      </div>

    </form>

    <!-- Modal de éxito -->
    <div v-if="modalVisible" id="modal-exito" class="modal" @click.self="cerrarModal">
      <div class="modal-content panel">
        <h2>✅ Guardado Exitoso</h2>
        <p>El paciente ha sido registrado correctamente.</p>
        <div class="form-actions">
          <button id="btn-aceptar" class="btn btn-primary" @click="aceptarModal">
            Aceptar
          </button>
          <button id="btn-agendar-cita" class="btn btn-warning" @click="agendarCita">
            Agendar Cita Ahora
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import api from '@/plugins/api';

const router = useRouter();
const route = useRoute();

// Estado del formulario - Paciente
const paciente = ref({
  id: null as number | null,
  nombre: '',
  apellidoPaterno: '',
  apellidoMaterno: '',
  fechaNacimiento: '',
  sexo: ''
});

// Estado del formulario - Tutor
const tutor = ref({
  nombre: '',
  apellidoPaterno: '',
  apellidoMaterno: '',
  correo: '',
  telefono: ''
});

// Estado del formulario - Dirección
const direccion = ref({
  calle: '',
  numeroExt: '',
  colonia: '',
  ciudad: '',
  estado: '',
  codigoPostal: ''
});

// Estado del modal
const modalVisible = ref(false);
const pacienteGuardadoId = ref<number | null>(null);

// Nombre completo del paciente
const nombreCompleto = computed(() => {
  const { nombre, apellidoPaterno, apellidoMaterno } = paciente.value;
  return `${nombre} ${apellidoPaterno} ${apellidoMaterno}`.trim();
});

// Cargar datos desde URL params (modo edición)
onMounted(() => {
  // Si viene un ID, cargar el paciente completo
  if (route.query.id) {
    cargarPaciente(Number(route.query.id));
  }

  // Pre-llenar desde query params (opcional)
  if (route.query.nombre) {
    paciente.value.nombre = route.query.nombre as string;
  }
  if (route.query.fecha_nacimiento) {
    paciente.value.fechaNacimiento = route.query.fecha_nacimiento as string;
  }
});

// Cargar paciente para editar
const cargarPaciente = async (id: number) => {
  try {
    const response = await api.get(`/pacientes/${id}`);
    const data = response.data;

    // Mapear datos del paciente
    paciente.value = {
      id: data.id,
      nombre: data.nombre,
      apellidoPaterno: data.apellidoPaterno,
      apellidoMaterno: data.apellidoMaterno || '',
      fechaNacimiento: data.fechaNacimiento,
      sexo: data.sexo
    };

    // Mapear datos del tutor
    if (data.tutor) {
      tutor.value = {
        nombre: data.tutor.nombre,
        apellidoPaterno: data.tutor.apellidoPaterno,
        apellidoMaterno: data.tutor.apellidoMaterno || '',
        correo: data.tutor.correo,
        telefono: data.tutor.telefono
      };
    }

    // Mapear dirección
    if (data.direccion) {
      direccion.value = {
        calle: data.direccion.calle,
        numeroExt: data.direccion.numeroExt,
        colonia: data.direccion.colonia,
        ciudad: data.direccion.ciudad,
        estado: data.direccion.estado,
        codigoPostal: data.direccion.codigoPostal
      };
    }
  } catch (error: any) {
    alert('Error al cargar el paciente');
    console.error('Error al cargar paciente:', error);
  }
};

// Guardar paciente (POST o PUT)
const guardarPaciente = async () => {
  try {
    // Construir el objeto completo
    const datosCompletos = {
      ...paciente.value,
      tutor: tutor.value,
      direccion: direccion.value
    };

    if (paciente.value.id) {
      // Actualizar paciente existente
      await api.put(`/pacientes/${paciente.value.id}`, datosCompletos);
      pacienteGuardadoId.value = paciente.value.id;
    } else {
      // Crear nuevo paciente
      const response = await api.post('/pacientes', datosCompletos);
      pacienteGuardadoId.value = response.data.id;
    }

    // Mostrar modal de éxito
    modalVisible.value = true;

  } catch (error: any) {
    alert(error.response?.data?.message || 'Error al guardar el paciente');
    console.error('Error al guardar:', error);
  }
};

// Aceptar modal (redirige al calendario)
const aceptarModal = () => {
  modalVisible.value = false;
  router.push('/calendario');
};

// Agendar cita (redirige al calendario con datos del paciente)
const agendarCita = () => {
  modalVisible.value = false;

  // Redirigir al calendario con el nombre completo como parámetro
  router.push({
    name: 'Calendario',
    query: {
      paciente: nombreCompleto.value,
      pacienteId: pacienteGuardadoId.value || undefined
    }
  });
};

// Cerrar modal
const cerrarModal = () => {
  modalVisible.value = false;
};

// Cancelar y volver
const cancelar = () => {
  router.push('/pacientes');
};
</script>

<style scoped>
.modal {
  display: flex;
}

.modal-content p {
  margin: 16px 0;
  font-size: 14px;
  color: #29475b;
}

.formulario {
  width: 100%;
}
</style>