<template>
  <div>
    <!-- Tarjeta de búsqueda -->
    <div class="card-paciente">
      <h2>Buscar Paciente</h2>
      <div class="form-grid">
        <div class="form-group">
          <label for="buscar_nombre">Nombre</label>
          <input
              type="text"
              id="buscar_nombre"
              v-model="busqueda.nombre"
              placeholder="Ej. Juan Pérez López"
          >
        </div>
      </div>
      <div class="form-actions">
        <button class="btn btn-primary" @click="buscarPacientes">
          <i class="fas fa-search"></i> Buscar
        </button>
        <button class="btn btn-warning" @click="limpiarBusqueda">
          <i class="fas fa-eraser"></i> Limpiar
        </button>
      </div>
    </div>

    <!-- Tarjeta listado de pacientes -->
    <div class="card-paciente">
      <h2>Listado de Pacientes</h2>
      <div class="table-responsive">
        <table class="tabla-pacientes" id="paciente_table">
          <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Apellido Paterno</th>
            <th>Apellido Materno</th>
            <th>Fecha Nacimiento</th>
            <th>Sexo</th>
            <th style="text-align: center;">Acciones</th>
          </tr>
          </thead>
          <tbody id="tabla-body">
          <tr v-for="paciente in pacientesFiltrados" :key="paciente.id">
            <td>{{ paciente.id }}</td>
            <td>{{ paciente.nombre }}</td>
            <td>{{ paciente.apellidoPaterno }}</td>
            <td>{{ paciente.apellidoMaterno }}</td>
            <td>{{ paciente.fechaNacimiento }}</td>
            <td>{{ paciente.sexo }}</td>
            <td style="text-align: center;">
              <button class="btn-admin-paciente" @click="administrarPaciente(paciente)">
                <i class="fas fa-user-cog"></i> Administrar
              </button>
            </td>
          </tr>
          <tr v-if="!loading && pacientesFiltrados.length === 0">
            <td colspan="7" style="text-align:center; padding:18px;">No se encontraron pacientes</td>
          </tr>
          <tr v-if="loading">
            <td colspan="7" style="text-align:center; padding:18px;">Cargando...</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="form-actions">
      <button class="btn btn-primary" @click="nuevoPaciente">
        <i class="fas fa-user-plus"></i> Nuevo paciente
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/plugins/api';

const router = useRouter();

// Estado
const busqueda = ref({
  nombre: ''
});

const pacientes = ref<Array<any>>([]);
const loading = ref(false);
const error = ref<string | null>(null);

// Cargar pacientes desde backend
const cargarPacientes = async (query = '') => {
  loading.value = true;
  error.value = null;
  try {
    let resp;
    if (query && query.trim().length > 0) {
      // Usar endpoint de búsqueda
      resp = await api.get('/api/pacientes/buscar', {
        params: { nombre: query }
      });
    } else {
      // Cargar todos los pacientes
      resp = await api.get('/api/pacientes');
    }

    pacientes.value = (resp.data || []).map((p: any) => ({
      id: p.id_paciente ?? p.id,
      nombre: p.nombre_paciente ?? p.nombre,
      apellidoPaterno: p.apellido_paterno ?? p.apellidoPaterno,
      apellidoMaterno: p.apellido_materno ?? p.apellidoMaterno,
      fechaNacimiento: formatDateShort(p.fecha_nacimiento ?? p.fechaNacimiento),
      sexo: p.sexo,
      raw: p
    }));
  } catch (err: any) {
    console.error('Error cargando pacientes', err);
    error.value = err?.response?.data?.message || 'Error al cargar pacientes';
    pacientes.value = [];
  } finally {
    loading.value = false;
  }
};

// Formatear fecha ISO (YYYY-MM-DD) -> YYYY-MM-DD (o vacío)
const formatDateShort = (d: any) => {
  if (!d) return '';
  try {
    const s = String(d);
    if (s.length >= 10) return s.substr(0, 10);
    return s;
  } catch {
    return '';
  }
};

// Computed - Filtrado local
const pacientesFiltrados = computed(() => {
  if (!busqueda.value.nombre.trim()) {
    return pacientes.value;
  }
  const nombreBusqueda = busqueda.value.nombre.trim().toLowerCase();
  return pacientes.value.filter(paciente => {
    const nombreCompleto = `${paciente.nombre} ${paciente.apellidoPaterno} ${paciente.apellidoMaterno}`.toLowerCase();
    return nombreCompleto.includes(nombreBusqueda);
  });
});

// Buscar (llama al backend con parámetro nombre)
const buscarPacientes = async () => {
  await cargarPacientes(busqueda.value.nombre);
};

// Limpiar
const limpiarBusqueda = async () => {
  busqueda.value.nombre = '';
  await cargarPacientes();
};

const administrarPaciente = (paciente: any) => {
  // Enviar id en params y el objeto raw (si está disponible) en el estado de navegación
  router.push({
    name: 'AdministrarPaciente',
    params: { id: String(paciente.id) },
    state: { paciente: paciente.raw ?? paciente }
  });
};

// Nuevo paciente
const nuevoPaciente = () => {
  router.push({ name: 'AgregarPaciente' });
};

// Cargar inicialmente todos los pacientes
onMounted(() => {
  cargarPacientes();
});
</script>

<style scoped>
/* Tarjeta blanca */
.card-paciente {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
  margin-bottom: 30px;
}

.card-paciente h2 {
  margin-bottom: 20px;
  font-size: 22px;
  color: #333;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 15px;
}

.form-group label {
  font-weight: bold;
  color: #555;
  display: block;
  margin-bottom: 5px;
}

.form-group input {
  width: 100%;
  padding: 8px 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  font-size: 14px;
}

.form-group input:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 2px rgba(56, 168, 214, 0.1);
}

.form-actions {
  margin-top: 20px;
  display: flex;
  gap: 15px;
  flex-wrap: wrap;
}

.table-responsive {
  overflow-x: auto;
}

/* Estilos de tabla de pacientes */
.tabla-pacientes {
  width: 100%;
  border-collapse: collapse;
  margin-top: 14px;
  background-color: var(--white-color);
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.tabla-pacientes th,
.tabla-pacientes td {
  border: 1px solid #ddd;
  padding: 10px 14px;
  font-size: 15px;
  text-align: left;
  color: #333;
}

.tabla-pacientes th {
  background-color: var(--primary-color);
  color: rgb(255, 255, 255);
  font-weight: 600;
  text-align: center;
}

.tabla-pacientes td:first-child {
  width: 60px;
  text-align: center;
  font-weight: bold;
  color: #333;
}

.tabla-pacientes tr:nth-child(even) {
  background-color: #f0f6fb;
}

.tabla-pacientes tr:nth-child(odd) {
  background-color: #ffffff;
}

.tabla-pacientes tr:hover {
  background-color: #e2edf7;
}

/* Botón Administrar Paciente */
.btn-admin-paciente {
  padding: 8px 16px;
  background-color: var(--primary-color);
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s;
  display: inline-flex;
  align-items: center;
  gap: 6px;
  text-decoration: none;
  font-size: 14px;
}

.btn-admin-paciente:hover {
  background-color: #2c8bb8;
  transform: translateY(-1px);
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

/* Botones generales */
.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s;
  display: inline-flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
}

.btn-primary {
  background-color: var(--primary-color);
  color: white;
}

.btn-primary:hover {
  background-color: #2c8bb8;
  transform: translateY(-1px);
}

.btn-warning {
  background-color: #f39c12;
  color: white;
}

.btn-warning:hover {
  background-color: #e67e22;
}
</style>