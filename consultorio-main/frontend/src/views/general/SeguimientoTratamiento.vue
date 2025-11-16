<template>
  <main class="content">
    <div class="treatment-section">
      <table class="treatment-table">
        <thead>
        <tr>
          <th>SEMANA</th>
          <th>DOSIS</th>
          <th>Fco. #1<br>Sublingual<br>Domingo</th>
          <th>Fco. #2<br>Sublingual<br>Jueves</th>
          <th>OBSERVACIONES</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>1ª</td>
          <td>.05 C.C.</td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td>2ª</td>
          <td>.10 C.C.</td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td>3ª</td>
          <td>.15 C.C.</td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td>4ª</td>
          <td>.20 C.C.</td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td>5ª</td>
          <td>.25 C.C.</td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td>6ª</td>
          <td>.30 C.C.</td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td>7ª</td>
          <td>.35 C.C.</td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td>8ª</td>
          <td>.40 C.C.</td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td>9ª</td>
          <td>.45 C.C.</td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <tr class="footer-row">
          <td>10ª</td>
          <td>.50 C.C.<br><small>Dosis de mantenimiento</small></td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        </tbody>
      </table>

      <div class="btn-group">
        <button class="btn btn-primary"><i class="fas fa-save"></i> Guardar</button>
        <button class="btn btn-warning"><i class="fas fa-edit"></i> Editar</button>
        <button class="btn btn-secondary" @click="window.print()"><i class="fas fa-print"></i> Imprimir</button>
        <button class="btn btn-danger" @click="volverAdministrar"><i class="fas fa-times"></i> Cancelar</button>
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';
import '@/styleSDT.css'

const router = useRouter();

const volverAdministrar = () => {
  router.push({ name: 'AdministrarPaciente' });
};
</script>

<style scoped>
/* Los estilos están en src/styleSDT.css */
</style>