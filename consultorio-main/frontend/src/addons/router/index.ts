import { createRouter, createWebHashHistory } from "vue-router";
import { securityStore } from "@/addons/store/general/security.ts";

let index = createRouter({
    history: createWebHashHistory(),
    routes: [
        // ========== RUTAS PÚBLICAS (guest: true) ==========
        {
            path: "/login",
            name: "Login",
            component: () => import("@/views/general/Login.vue"),
            meta: { guest: true },
            beforeEnter: () => { return securityStore().getJwt ? false : true }
        },
        {
            path: "/confirmar-nueva-password",
            name: "ConfirmarNuevaPassword",
            component: () => import("@/views/general/ConfirmarNuevaPassword.vue"),
            meta: { guest: true }
        },
        {
            path: "/nueva-password-generada",
            name: "NuevaPasswordGenerada",
            component: () => import("@/views/general/NuevaPasswordGenerada.vue"),
            meta: { guest: true }
        },

        // ========== RUTAS PROTEGIDAS (requieren autenticación) ==========
        {
            path: "/",
            name: "Index",
            component: () => import("@/views/general/Index.vue"),
            redirect: "/calendario",
            children: [
                { path: "/calendario", name: "Calendario", component: () => import("@/views/general/MenuCalendario.vue") },
                { path: "/inventario", name: "Inventario", component: () => import("@/views/general/GestionInventario.vue") },
                { path: "/pacientes", name: "Pacientes", component: () => import("@/views/general/MenuPacientes.vue") },
                { path: "/pagos", name: "Pagos", component: () => import("@/views/general/GestionPagos.vue") },
                { path: "/administrar-paciente", name: "AdministrarPaciente", component: () => import("@/views/general/AdministrarPaciente.vue") },
                { path: "/reportes", name: "GestionReportes", component: () => import("@/views/general/GestionReportes.vue") },
                {
                    path: "/pruebas-alergias",
                    name: "PruebasAlergias",
                    component: () => import("@/views/general/PruebasAlergias.vue")
                }
            ]
        }
    ],
    scrollBehavior() {
        return { top: 0 };
    }
});

index.beforeEach(async (to, _from, next) => {
    if (to.meta.guest) {
        next();
    } else {
        //await securityStore().verifyJwt();
        next();
    }
});

export default index;