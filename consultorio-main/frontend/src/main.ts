import { createApp } from 'vue'

import './calendar-styles.css'
import './style_gestion_de_pago.css'
import './style_agregar_paciente.css'
//import './styleSDT.css'
import './style.css'


import App from './App.vue'

import router from "@/addons/router/index.ts";
import { setup } from "@/plugins/setupInterceptor.ts";
import { createPinia } from "pinia";
import {createPersistedState} from "pinia-plugin-persistedstate";
import PrimeVue from "primevue/config";
import ToastService from "primevue/toastservice";
import Tooltip from "primevue/tooltip";
import Aura from '@primeuix/themes/aura';

import "@fortawesome/fontawesome-free/css/all.min.css";



const app = createApp(App);
const pinia = createPinia();
// createApp(App).mount('#app')

pinia.use(createPersistedState({
    storage: localStorage
}));

app.use(pinia);
app.use(PrimeVue, {
    theme: {
        preset: Aura,
        options: {
            prefix: 'p',
            darkModeSelector: 'system',
            cssLayer: false
        }
    },
    locale:{
        dayNames: ["Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"],
        monthNames: ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"],
        monthNamesShort: ["En", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Agto", "Sept", "Oct", "Nov", "Dic"],
        dayNamesMin: ["Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sa"],
        firstDayOfWeek: 0,
    }
});
app.use(ToastService);
app.use(router);

app.directive('tooltip', Tooltip);

setup();
app.mount("#app");
export default app