<template>
  <div class="main-content">
    <main class="content">
      <div class="editor-container">
        <!-- Barra de herramientas -->
        <div id="toolbar">
          <button @click="formatDoc('bold')"><b>B</b></button>
          <button @click="formatDoc('italic')"><i>I</i></button>
          <button @click="formatDoc('underline')"><u>U</u></button>
          <button @click="formatDoc('insertOrderedList')"><i class="fas fa-list-ol"></i></button>
          <button @click="formatDoc('insertUnorderedList')"><i class="fas fa-list-ul"></i></button>
        </div>

        <!-- Área editable -->
        <div id="editor" contenteditable="true" ref="editor">
          <h2>Documento de ejemplo</h2>
          <p>
            Aquí puedes redactar tus reportes, notas o informes clínicos. Usa los botones de formato
            para aplicar negritas, cursivas o subrayado.
          </p>
        </div>
      </div>

      <!-- Botón principal -->
      <div class="actions">
        <button class="btn-export" @click="noImplementado">
          Exportar a PDF
        </button>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { securityStore } from "@/addons/store/general/security";

const editor = ref(null);
const security = securityStore();

const logout = () => {
  security.logout();
};

const formatDoc = (command, value = null) => {
  document.execCommand(command, false, value);
};

const noImplementado = () => {
  alert("La funcionalidad de exportar a PDF aún no está implementada.");
};
</script>

<style scoped>
/* ======== CONTENEDOR GENERAL ======== */
.main-content {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background: transparent;
}

.content {
  padding: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

/* ======== EDITOR ======== */
.editor-container {
  width: 100%;
  max-width: 1250px; /* más grande, ocupa el área translúcida completa */
  background: #ffffff;
  padding: 25px 35px;
  border-radius: 12px;
  box-shadow: 0 3px 18px rgba(0, 0, 0, 0.08);
  margin-top: 5px;
}

/* ======== TOOLBAR ======== */
#toolbar {
  display: flex;
  gap: 8px;
  justify-content: flex-start;
  margin-bottom: 15px;
  flex-wrap: wrap;
}

#toolbar button {
  background: #ffffff;
  border: 1px solid #ccc;
  color: #333;
  border-radius: 6px;
  padding: 6px 10px;
  cursor: pointer;
  font-size: 15px;
  transition: 0.2s;
}

#toolbar button:hover {
  background: #f2f2f2;
  border-color: #999;
}

/* ======== ÁREA EDITABLE ======== */
#editor {
  border: 1px solid #ddd;
  min-height: 380px;
  padding: 18px 20px;
  border-radius: 8px;
  background: #fafafa;
  outline: none;
  color: #333;
  font-size: 15px;
  line-height: 1.6;
}

#editor:focus {
  border-color: #38a8d6;
  background: #fff;
}

/* ======== BOTÓN EXPORTAR ======== */
.actions {
  margin-top: 25px;
  display: flex;
  justify-content: flex-end;
  width: 100%;
  max-width: 1250px;
}

.btn-export {
  background: #38a8d6;
  color: white;
  font-weight: 600;
  padding: 12px 30px;
  font-size: 15px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-export:hover {
  background: #2c8bb8;
  transform: translateY(-1px);
}

/* ======== RESPONSIVE ======== */
@media (max-width: 992px) {
  .editor-container {
    max-width: 95%;
    padding: 18px 22px;
  }

  .actions {
    justify-content: center;
  }
}
</style>
