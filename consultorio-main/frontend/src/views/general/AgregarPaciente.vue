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
            <input type="number" id="numero_ext" v-model="direccion.numeroExt" required>
          </div>

          <div class="form-group">
            <label for="colonia">Colonia</label>
            <input type="text" id="colonia" v-model="direccion.colonia" required>
          </div>

          <div class="form-group">
            <label for="estado">Estado</label>
            <input type="text" id="estado" v-model="direccion.estado" required>
          </div>

          <div class="form-group">
            <label for="codigo_postal">Código Postal</label>
            <input type="number" id="codigo_postal" v-model="direccion.codigoPostal" required>
          </div>
        </div>
      </div>

      <!-- Botones -->
      <div class="panel" style="padding-bottom:16px;">
        <div class="form-actions">
          <button
              type="button"
              class="btn btn-primary"
              @click="guardarPaciente"
              :disabled="guardando"
          >
            <i class="fas fa-save"></i> {{ guardando ? 'Guardando...' : 'Guardar' }}
          </button>

          <button type="button" class="btn btn-danger btn-cancelar" @click="cancelar" :disabled="guardando">
            <i class="fas fa-times"></i> Cancelar
          </button>
        </div>
      </div>

    </form>

    <!-- Modal de éxito -->
    <div v-if="modalVisible" id="modal-exito" class="modal" @click.self="cerrarModal">
      <div class="modal-content panel">
        <h2>Guardado Exitoso</h2>
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
  sexo: '',
  estatus: true
});

// Estado del formulario - Tutor (agregamos id para saber si existe)
const tutor = ref({
  id_tutor: null as number | null,
  nombre: '',
  apellidoPaterno: '',
  apellidoMaterno: '',
  correo: '',
  telefono: '',
  estatus: true
});

// Estado del formulario - Dirección (agregamos id para saber si existe)
const direccion = ref({
  id_direccion: null as number | null,
  calle: '',
  numeroExt: 0,
  colonia: '',
  estado: '',
  codigoPostal: 0,
  estatus: true
});

// Estado del modal
const modalVisible = ref(false);
const guardando = ref(false);
const pacienteGuardadoId = ref<number | null>(null);

// Nombre completo del paciente
const nombreCompleto = computed(() => {
  const { nombre, apellidoPaterno, apellidoMaterno } = paciente.value;
  return `${nombre} ${apellidoPaterno} ${apellidoMaterno}`.trim();
});

// Cargar datos desde URL params (modo edición)
onMounted(() => {
  if (route.query.id || route.params.id) {
    const idParam = Number(route.query.id ?? route.params.id);
    if (!isNaN(idParam)) cargarPaciente(idParam);
  }

  if (route.query.nombre) {
    paciente.value.nombre = route.query.nombre as string;
  }
  if (route.query.fecha_nacimiento) {
    paciente.value.fechaNacimiento = route.query.fecha_nacimiento as string;
  }

  // Si la navegación incluyó state con paciente/tutor/direccion (por ejemplo desde AdministrarPaciente), usarlo para precargar
  try {
    const hs: any = (typeof window !== 'undefined' && window.history && window.history.state) ? window.history.state : null;
    const current = hs?.current ?? hs;
    if (current?.paciente) {
      const p = current.paciente;
      paciente.value = {
        id: p.id ?? p.id_paciente ?? null,
        nombre: p.nombre ?? p.nombre_paciente ?? '',
        apellidoPaterno: p.apellidoPaterno ?? p.apellido_paterno ?? '',
        apellidoMaterno: p.apellidoMaterno ?? p.apellido_materno ?? '',
        fechaNacimiento: p.fechaNacimiento ?? p.fecha_nacimiento ?? '',
        sexo: p.sexo ?? '',
        estatus: p.estatus ?? true
      };
    }
    if (current?.tutor) {
      const t = current.tutor;
      tutor.value = {
        id_tutor: t.id_tutor ?? t.id ?? null,
        nombre: t.nombre ?? t.nombre_tutor ?? '',
        apellidoPaterno: t.apellidoPaterno ?? t.apellido_paterno ?? '',
        apellidoMaterno: t.apellidoMaterno ?? t.apellido_materno ?? '',
        correo: t.correo ?? t.email ?? '',
        telefono: t.telefono ?? t.telefonoMovil ?? '',
        estatus: t.estatus ?? true
      };
    }
    if (current?.direccion) {
      const d = current.direccion;
      direccion.value = {
        id_direccion: d.id_direccion ?? d.id ?? null,
        calle: d.calle ?? '',
        numeroExt: d.numero_ext ?? d.numeroExt ?? 0,
        colonia: d.colonia ?? '',
        estado: d.estado ?? '',
        codigoPostal: d.codigo_postal ?? d.codigoPostal ?? 0,
        estatus: d.estatus ?? true
      };
    }
  } catch (e) {
    console.warn('No se pudo leer history state en AgregarPaciente:', e);
  }
});

// Cargar paciente para editar
const cargarPaciente = async (id: number) => {
  try {
    const response = await api.get(`/api/pacientes/${id}`);
    const data = response.data;

    paciente.value = {
      id: data.id_paciente ?? data.id,
      nombre: data.nombre_paciente ?? data.nombre,
      apellidoPaterno: data.apellido_paterno ?? data.apellidoPaterno,
      apellidoMaterno: data.apellido_materno ?? data.apellidoMaterno,
      fechaNacimiento: data.fecha_nacimiento ?? data.fechaNacimiento,
      sexo: data.sexo,
      estatus: data.estatus ?? true
    };

    // Cargar tutor
    try {
      const tutorResp = await api.get(`/api/pacientes/${id}/tutores`);
      if (tutorResp?.data && tutorResp.data.length > 0) {
        const t = tutorResp.data[0];
        tutor.value = {
          id_tutor: t.id_tutor ?? t.id,
          nombre: t.nombre_tutor ?? t.nombre,
          apellidoPaterno: t.apellido_paterno ?? t.apellidoPaterno,
          apellidoMaterno: t.apellido_materno ?? t.apellidoMaterno,
          correo: t.correo ?? t.email ?? '',
          telefono: t.telefono ?? t.telefonoMovil ?? '',
          estatus: t.estatus ?? true
        };

        // Cargar dirección del tutor
        if (tutor.value.id_tutor) {
          try {
            const dirResp = await api.get(`/api/pacientes/tutores/${tutor.value.id_tutor}/direcciones`);
            if (dirResp?.data && dirResp.data.length > 0) {
              const d = dirResp.data[0];
              direccion.value = {
                id_direccion: d.id_direccion ?? d.id ?? null,
                calle: d.calle ?? '',
                numeroExt: d.numero_ext ?? d.numeroExt ?? 0,
                colonia: d.colonia ?? '',
                estado: d.estado ?? '',
                codigoPostal: d.codigo_postal ?? d.codigoPostal ?? 0,
                estatus: d.estatus ?? true
              };
            }
          } catch (dirError) {
            console.warn('Error cargando dirección del tutor:', dirError);
          }
        }
      }
    } catch (tutorError) {
      console.warn('Error cargando tutor del paciente:', tutorError);
    }
  } catch (error: any) {
    console.error('Error al cargar el paciente:', error);
    alert('Error al cargar el paciente');
  }
};

const guardarPaciente = async () => {
  guardando.value = true;

  try {
    // Validar campos requeridos del paciente
    if (!paciente.value.nombre || !paciente.value.apellidoPaterno || !paciente.value.fechaNacimiento || !paciente.value.sexo) {
      alert('Por favor complete todos los campos requeridos del paciente');
      guardando.value = false;
      return;
    }

    // Preparar datos para enviar en una sola petición
    const datosCompletos: any = {
      nombre_paciente: paciente.value.nombre,
      apellido_paterno: paciente.value.apellidoPaterno,
      apellido_materno: paciente.value.apellidoMaterno || '',
      fecha_nacimiento: paciente.value.fechaNacimiento,
      sexo: paciente.value.sexo,
      estatus: paciente.value.estatus
    };

    console.log('Datos del paciente a enviar:', datosCompletos);

    let response;

    if (paciente.value.id) {
      // Actualizar solo paciente existente
      const pacienteData = {
        nombre_paciente: paciente.value.nombre,
        apellido_paterno: paciente.value.apellidoPaterno,
        apellido_materno: paciente.value.apellidoMaterno,
        fecha_nacimiento: paciente.value.fechaNacimiento,
        sexo: paciente.value.sexo,
        estatus: paciente.value.estatus
      };

      response = await api.put(`/api/pacientes/${paciente.value.id}`, pacienteData);
      pacienteGuardadoId.value = paciente.value.id;

      // Actualizar/crear tutor si hay datos
      if (tutor.value.nombre && tutor.value.apellidoPaterno && tutor.value.correo && tutor.value.telefono) {
        try {
          const tutorData: any = {
            id_tutor: tutor.value.id_tutor ?? undefined,
            nombre_tutor: tutor.value.nombre,
            apellido_paterno: tutor.value.apellidoPaterno,
            apellido_materno: tutor.value.apellidoMaterno || '',
            correo: tutor.value.correo,
            telefono: tutor.value.telefono,
            estatus: tutor.value.estatus,
            id_paciente: paciente.value.id
          };

          const tutorResponse = await api.post(`/api/pacientes/${paciente.value.id}/tutores`, tutorData);
          if (tutorResponse?.data) {
            tutor.value.id_tutor = tutorResponse.data.id_tutor ?? tutorResponse.data.id ?? tutor.value.id_tutor;
          }
        } catch (tutorError) {
          console.warn('Error actualizando/creando tutor:', tutorError);
        }
      }

      // Actualizar/crear dirección si hay datos y tutorId disponible
      if (direccion.value.calle && direccion.value.colonia && direccion.value.estado && direccion.value.codigoPostal && tutor.value.id_tutor) {
        try {
          const direccionData: any = {
            id_direccion: direccion.value.id_direccion ?? undefined,
            calle: direccion.value.calle,
            numero_ext: direccion.value.numeroExt,
            colonia: direccion.value.colonia,
            estado: direccion.value.estado,
            codigo_postal: direccion.value.codigoPostal,
            estatus: direccion.value.estatus,
            id_tutor: tutor.value.id_tutor
          };

          const direccionResp = await api.post(`/api/pacientes/tutores/${tutor.value.id_tutor}/direcciones`, direccionData);
          if (direccionResp?.data) {
            direccion.value.id_direccion = direccionResp.data.id_direccion ?? direccionResp.data.id ?? direccion.value.id_direccion;
          }
        } catch (dirError) {
          console.warn('Error actualizando/creando direccion:', dirError);
        }
      }

      alert('Paciente actualizado correctamente');
      // Redirect back to AdministrarPaciente para ese paciente
      router.push({ name: 'AdministrarPaciente', params: { id: String(paciente.value.id) } });
    } else {
      // PRIMERO: Crear solo el paciente sin tutor/dirección
      response = await api.post('/api/pacientes', datosCompletos);
      console.log('Respuesta del servidor (paciente):', response.data);

      if (response.data && response.data.id_paciente) {
        pacienteGuardadoId.value = response.data.id_paciente;

        // SEGUNDO: Si hay datos de tutor, crear tutor
        if (tutor.value.nombre && tutor.value.apellidoPaterno && tutor.value.correo && tutor.value.telefono) {
          try {
            const tutorData = {
              nombre_tutor: tutor.value.nombre,
              apellido_paterno: tutor.value.apellidoPaterno,
              apellido_materno: tutor.value.apellidoMaterno || '',
              correo: tutor.value.correo,
              telefono: tutor.value.telefono,
              estatus: tutor.value.estatus
            };

            console.log('Datos del tutor a enviar:', tutorData);

            const tutorResponse = await api.post(`/api/pacientes/${pacienteGuardadoId.value}/tutores`, tutorData);
            console.log('Respuesta del servidor (tutor):', tutorResponse.data);

            // TERCERO: Si hay datos de dirección y se creó el tutor, crear dirección
            if (direccion.value.calle && direccion.value.colonia && direccion.value.estado && direccion.value.codigoPostal && tutorResponse.data.id_tutor) {
              try {
                const direccionData = {
                  calle: direccion.value.calle,
                  numero_ext: direccion.value.numeroExt,
                  colonia: direccion.value.colonia,
                  estado: direccion.value.estado,
                  codigo_postal: direccion.value.codigoPostal,
                  estatus: direccion.value.estatus
                };

                console.log('Datos de dirección a enviar:', direccionData);

                await api.post(`/api/pacientes/tutores/${tutorResponse.data.id_tutor}/direcciones`, direccionData);
                console.log('Dirección creada exitosamente');
              } catch (direccionError: any) {
                console.warn('Error creando dirección:', direccionError);
                // No mostrar alerta para errores de dirección
              }
            }
          } catch (tutorError: any) {
            console.warn('Error creando tutor:', tutorError);
            // No mostrar alerta para errores de tutor
          }
        }

        modalVisible.value = true;
      } else {
        throw new Error('No se recibió ID del paciente creado');
      }
    }

  } catch (error: any) {
    console.error('Error completo al guardar paciente:', error);

    // Manejo de errores más robusto
    let errorMsg = 'Error al guardar el paciente';

    if (error?.response?.data) {
      console.log('Respuesta de error:', error.response.data);
      errorMsg = error.response.data.error ||
          error.response.data.message ||
          `Error ${error.response.status}`;
    } else if (error?.request) {
      errorMsg = 'No se pudo conectar con el servidor';
    } else {
      errorMsg = error?.message || 'Error desconocido';
    }

    alert(errorMsg);
  } finally {
    guardando.value = false;
  }
};

// Aceptar modal (redirige al calendario)
const aceptarModal = () => {
  modalVisible.value = false;
  router.push('/pacientes');
};

// Agendar cita (redirige al calendario con datos del paciente)
const agendarCita = () => {
  modalVisible.value = false;
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

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
</style>