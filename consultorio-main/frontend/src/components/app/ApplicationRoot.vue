<template>
  <div>
    <!-- Toggle Button -->
    <button id="toggle-btn" class="toggle-btn" @click="toggleSidebar">
      <i class="fas fa-bars"></i>
    </button>

    <!-- Sidebar -->
    <nav id="sidebar" class="sidebar" :class="{ open: sidebarOpen }">
      <div class="sidebar-section">
        <h3 class="sidebar-heading">MENU</h3>
        <router-link to="/calendario" class="sidebar-link" @click="closeSidebar">
          <i class="fas fa-home"></i><span>Inicio / Calendario</span>
        </router-link>
        <router-link to="/pacientes" class="sidebar-link" @click="closeSidebar">
          <i class="fas fa-user-edit"></i><span>Gestionar Pacientes</span>
        </router-link>
        <router-link to="/pagos" class="sidebar-link" @click="closeSidebar">
          <i class="fas fa-money-bill-wave"></i><span>Gestionar Pagos</span>
        </router-link>
        <router-link to="/inventario" class="sidebar-link" @click="closeSidebar">
          <i class="fas fa-boxes-stacked"></i><span>Gestión de Inventario</span>
        </router-link>
      </div>
    </nav>

    <!-- Main Content -->
    <div class="main-content">
      <!-- Header -->
      <header class="header">
        <div class="header-left">
          <div class="header-logo">
            <img src="@/assets/Logo.png" alt="Logo del Consultorio">
          </div>
          <router-link to="/calendario" class="home-btn" @click="closeSidebar">
            <i class="fas fa-home"></i>
          </router-link>
        </div>
        <div class="header-center">
          <h1>{{ pageTitle }}</h1>
        </div>
        <div class="header-right">
          <router-link to="/calendario" class="calendar-btn" @click="closeSidebar">
            <i class="fas fa-calendar-day"></i>
          </router-link>
          <button @click="logout" class="logout-btn">
            Cerrar Sesión <i class="fas fa-sign-out-alt"></i>
          </button>
        </div>
      </header>

      <!-- Content Area -->
      <main class="content">
        <router-view />
      </main>

      <!-- Footer -->
      <footer class="footer">
        <p>&copy; 2025 Mococitos.</p>
      </footer>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, onMounted, onBeforeUnmount } from 'vue';
import { useRoute } from 'vue-router';
import { securityStore } from '@/addons/store/general/security';

const route = useRoute();
const security = securityStore();

const sidebarOpen = ref(false);
const pageTitle = ref('Inicio / Calendario');

const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value;
  document.body.classList.toggle('sidebar-open', sidebarOpen.value);
};

// 🔹 Nueva función universal para cerrar el menú
const closeSidebar = () => {
  sidebarOpen.value = false;
  document.body.classList.remove('sidebar-open');
};

// 🔹 Cerrar sesión
const logout = () => {
  security.logout();
};

// 🔹 Actualizar título según la ruta y cerrar sidebar
watch(
    () => route.path,
    (newPath) => {
      const titles: Record<string, string> = {
        '/calendario': 'Inicio / Calendario',
        '/pacientes': 'Gestionar Pacientes',
        '/pagos': 'Gestionar Pagos',
        '/inventario': 'Gestión de Inventario',
        '/administrar-paciente': 'Administrar Pacientes',
        '/reportes': 'Editor de Documentos ',
        '/pruebas-alergias': 'Pruebas de Alergias',
        '/agregar-paciente': 'Agregar Paciente',
        '/historial-clinico': 'Gestión de Historial Clínico',
        '/vacunas': 'Registro de Pruebas y Tratamiento',
        '/seguimiento': 'Seguimiento de Tratamiento',

      };
      pageTitle.value = titles[newPath] || 'Dashboard';

      // Siempre cerrar el menú al cambiar ruta
      closeSidebar();
    },
    { immediate: true }
);

// 🔹 Detectar navegación por historial (botón atrás/adelante)
const handlePopState = () => {
  closeSidebar();
};

// Registrar y limpiar evento del historial
onMounted(() => {
  window.addEventListener('popstate', handlePopState);
});
onBeforeUnmount(() => {
  window.removeEventListener('popstate', handlePopState);
});
</script>

<style scoped>
/* Tus estilos globales están en style.css */
</style>
