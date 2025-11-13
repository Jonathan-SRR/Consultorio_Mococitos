<template>
  <div class="main-content">
    <div class="content">
      <div class="login-container">
        <div class="login-logo">
          <img src="@/assets/Logo.png" alt="Logo" />
        </div>
        <h1>Iniciar sesión</h1>

        <form id="form-simple" @submit.prevent="handleLogin">
          <div class="form-group">
            <label for="usuario">Usuario</label>
            <input
                type="text"
                id="usuario"
                name="usuario"
                v-model="cred.username"
                required
            />
          </div>

          <div class="form-group">
            <label for="clave">Contraseña</label>
            <input
                type="password"
                id="clave"
                name="clave"
                v-model="cred.password"
                required
            />
          </div>

          <div class="form-buttons">
            <button
                type="submit"
                class="btn btn-primary"
            >
              Iniciar sesión
            </button>
            <button
                type="button"
                id="btn-back-simple"
                class="btn btn-secondary"
                @click="irARecuperarPassword"
            >
              Enviar contraseña por email
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { securityStore } from '@/addons/store/general/security';

const router = useRouter();
const security = securityStore();

const cred = ref({
  username: '',
  password: ''
});

const handleLogin = () => {
  security.login(cred.value);
  // Nota: El router.push se hace automáticamente en security.login()
  // que redirige a 'Home', pero podemos cambiarlo
};

const irARecuperarPassword = () => {
  router.push({ name: 'ConfirmarNuevaPassword' });
};
</script>

<style scoped>
/* Estilos adicionales específicos del login */
.login-container {
  max-width: 400px;
  margin: 80px auto;
  padding: 30px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
  color: black;
}

.login-logo {
  margin-bottom: 20px;
}

.login-logo img {
  height: 150px;
}

.login-container h1 {
  margin-bottom: 25px;
  color: var(--primary-color);
}

#form-simple {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-top: 15px;
}

.form-buttons {
  display: flex;
  flex-direction: column;
  gap: 10px;
  align-items: center;
}

.form-buttons .btn {
  width: 100%;
  max-width: 250px;
}
</style>