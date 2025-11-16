<template>
  <div>
    <section id="inventory-detailed" style="margin-top: 20px;">
      <div class="antigen-table-container">
        <h2>Antígenos</h2>
        <table id="product-table">
          <thead>
          <tr>
            <th>Seleccionar</th>
            <th>ID</th>
            <th>Nombre</th>
            <th>Fecha de Caducidad</th>
            <th>Categoría</th>
            <th>Cantidad</th>
            <th>Lote</th>
            <th>Permiso de Salubridad</th>
            <th>Acciones</th>
          </tr>
          </thead>
          <tbody id="product-list">
          <tr v-for="product in products" :key="product.id">
            <td>
              <input
                  type="checkbox"
                  class="chk-reposicion"
                  :value="product.id"
                  v-model="selectedProducts"
              >
            </td>
            <td>{{ product.id }}</td>
            <td>{{ product.nombre }}</td>
            <td>{{ product.fechaCaducidad }}</td>
            <td>{{ product.categoria }}</td>
            <td>{{ product.cantidad }}</td>
            <td>{{ product.lote }}</td>
            <td>{{ product.permiso }}</td>
            <td>
              <button class="btn btn-primary btn-edit" @click="editarProducto(product)">
                <i class="fas fa-edit"></i> Editar
              </button>
              <button class="btn btn-danger btn-delete" @click="eliminarProducto(product.id)">
                <i class="fas fa-trash"></i> Eliminar
              </button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>

      <div class="btn-container">
        <button id="btn-add" class="btn btn-primary" @click="abrirModal('add')">
          <i class="fas fa-plus"></i> Agregar
        </button>
        <button id="btn-request" class="btn btn-primary" @click="pedirReposicion">
          <i class="fas fa-truck"></i> Pedir Reposición
        </button>
      </div>
    </section>

    <!-- Modal -->
    <div class="modal" v-if="showModal" @click.self="cerrarModal">
      <div class="modal-content">
        <div class="modal-header">
          <h3 class="modal-title">{{ modalTitle }}</h3>
          <button class="close-btn" @click="cerrarModal">&times;</button>
        </div>
        <form id="form-simple" @submit.prevent="guardarProducto">
          <label>
            ID:
            <input
                type="text"
                v-model="formData.id"
                :disabled="isEditing"
                required
            >
          </label>
          <label>
            Nombre:
            <input type="text" v-model="formData.nombre" required>
          </label>
          <label>
            Fecha de Caducidad:
            <input type="date" v-model="formData.fechaCaducidad" required>
          </label>
          <label>
            Categoría:
            <select v-model="formData.categoria" required>
              <option value="">Seleccione</option>
              <option value="C001">C001</option>
              <option value="C002">C002</option>
              <option value="C003">C003</option>
              <option value="Otro">Otro</option>
            </select>
          </label>
          <label>
            Cantidad mL:
            <input type="number" v-model.number="formData.cantidad" min="0" required>
          </label>
          <label>
            Lote:
            <input type="text" v-model="formData.lote" required>
          </label>
          <label>
            Permiso de Salubridad:
            <input type="text" v-model="formData.permiso" required>
          </label>

          <div class="form-buttons">
            <button type="submit" class="btn btn-primary">
              <i class="fas fa-save"></i> Guardar
            </button>
            <button type="button" class="btn btn-danger" @click="cerrarModal">
              <i class="fas fa-times"></i> Cancelar
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import api from '@/plugins/api';

// Estado
const products = ref<any[]>([]);

const showModal = ref(false);
const modalTitle = ref('Agregar Antígeno');
const isEditing = ref(false);
const selectedProducts = ref<string[]>([]);

const formData = ref({
  id: '',
  nombre: '',
  fechaCaducidad: '',
  categoria: '',
  cantidad: 0,
  lote: '',
  permiso: ''
});

const fetchProducts = async () => {
  try {
    const res = await api.get('/api/antigenos');
    const raw: any[] = res.data || [];

    products.value = raw.map(r => ({
      id: r.id,
      nombre: r.nombre_antigeno ?? r.nombre ?? '',
      fechaCaducidad: r.fecha_caducidad
          ? (typeof r.fecha_caducidad === 'string'
              ? r.fecha_caducidad
              : r.fecha_caducidad.toString())
          : '',
      categoria: r.categoria ?? '',
      cantidad: r.cantidad ?? r.cantidad_ml ?? 0,
      lote: r.lote ?? '',
      permiso: r.permiso ?? ''
    }));

    // Proximos a caducar
    const hoy = new Date();
    const limite = new Date();
    limite.setMonth(hoy.getMonth() + 3); // dentro de 3 meses

    const proximos = products.value.filter(p => {
      const fecha = new Date(p.fechaCaducidad);
      return fecha >= hoy && fecha <= limite;
    });

    if (proximos.length > 0) {
      const nombres = proximos.map(p => `• ${p.nombre} (caduca: ${p.fechaCaducidad})`).join('\n');
      alert(` Los siguientes antígenos están próximos a caducar:\n\n${nombres}`);
    }
  } catch (e) {
    console.error('Error cargando antígenos:', e);
  }
};

onMounted(() => {
  fetchProducts();
});

// Funciones
const abrirModal = (mode: 'add' | 'edit', product: any = null) => {
  if (mode === 'edit' && product) {
    modalTitle.value = 'Editar Antígeno';
    isEditing.value = true;
    formData.value = { ...product };
  } else {
    modalTitle.value = 'Agregar Antígeno';
    isEditing.value = false;
    formData.value = {
      id: '',
      nombre: '',
      fechaCaducidad: '',
      categoria: '',
      cantidad: 0,
      lote: '',
      permiso: ''
    };
  }
  showModal.value = true;
};

const cerrarModal = () => {
  showModal.value = false;
};

const guardarProducto = async () => {

  const nombreExistente = products.value.some(p =>
      p.nombre.trim().toLowerCase() === formData.value.nombre.trim().toLowerCase() &&
      (!isEditing.value || p.id !== formData.value.id)
  );

  if (nombreExistente) {
    alert('Ya existe un antígeno con ese nombre.');
    return;
  }

  try {
    const dataToSend = {
      id: formData.value.id,
      nombre_antigeno: formData.value.nombre,
      fecha_caducidad: formData.value.fechaCaducidad,
      categoria: formData.value.categoria,
      cantidad: formData.value.cantidad,
      lote: formData.value.lote,
      permiso: formData.value.permiso
    };

    if (isEditing.value) {
      await api.put(`/api/antigenos/${formData.value.id}`, dataToSend);
      alert('Antígeno actualizado correctamente.');
    } else {
      await api.post('/api/antigenos', dataToSend);
      alert('Antígeno agregado correctamente.');
    }

    cerrarModal();
    await fetchProducts();
  } catch (e) {
    console.error('Error guardando antígeno:', e);
    alert('Ocurrió un error al guardar el antígeno.');
  }
};



const editarProducto = (product: any) => {
  abrirModal('edit', product);
};

const eliminarProducto = async (id: string) => {
  if (confirm('¿Está seguro que desea eliminar este antígeno?')) {
    try {
      await api.delete(`/api/antigenos/${id}`);
      alert('Antígeno eliminado correctamente.');
      await fetchProducts();
    } catch (e) {
      console.error('Error eliminando antígeno:', e);
      alert('Error al eliminar el antígeno.');
    }
  }
};


const pedirReposicion = () => {
  if (selectedProducts.value.length === 0) {
    alert('No has seleccionado productos para reposición.');
    return;
  }

  const lista = selectedProducts.value.map(id => {
    const product = products.value.find(p => p.id === id);
    return product ? `${product.id} - ${product.nombre}` : '';
  }).filter(Boolean);

  alert(`Solicitud enviada al laboratorio para reposición de:\n\n${lista.join('\n')}`);
  selectedProducts.value = [];
};




</script>

<style scoped>
/* Estilos específicos del modal */
.modal {
  display: flex;
  position: fixed;
  z-index: 1002;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.4);
  align-items: center;
  justify-content: center;
}

.modal-content {
  background-color: #fefefe;
  padding: 25px;
  border: 1px solid #888;
  width: 90%;
  max-width: 600px;
  border-radius: 10px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
  position: relative;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 2px solid var(--secondary-color);
}

.modal-title {
  color: var(--primary-color);
  margin: 0;
}

.close-btn {
  background: none;
  border: none;
  font-size: 28px;
  font-weight: bold;
  color: #aaa;
  cursor: pointer;
  padding: 0;
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.close-btn:hover {
  color: #000;
}

#form-simple {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
}

#form-simple label {
  flex: 1 1 calc(50% - 15px);
  display: flex;
  flex-direction: column;
  font-weight: 600;
  font-size: 0.9rem;
  color: var(--text-color);
}

#form-simple input,
#form-simple select {
  margin-top: 5px;
  padding: 8px;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.form-buttons {
  flex-basis: 100%;
  margin-top: 15px;
  display: flex;
  gap: 10px;
  justify-content: flex-end;
}
</style>