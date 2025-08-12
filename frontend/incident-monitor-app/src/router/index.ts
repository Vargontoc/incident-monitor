import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "../stores/auth";
import LoginView from "../views/LoginView.vue";
import IncidentsView from "../views/incidents/IncidentsView.vue";
import IncidentEditView from "../views/incidents/IncidentEditView.vue";
import IncidentDetailView from "../views/incidents/IncidentDetailView.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/login', name: 'login', component: LoginView},
        { path: '/', redirect: '/incidents' },
        { path: '/incidents', name: 'incidents', component: IncidentsView, meta: { auth: true }},
        { path: '/incidents/new', name: 'incidents-new', component: IncidentEditView, meta: { auth: true }},
        { path: '/incidents/:id', name: 'incidents-detail', component: IncidentDetailView, meta: { auth: true }},
        { path: '/incidents/:id/edit', name: 'incidents-edit', component: IncidentEditView, meta: { auth: true }}
    ]
})

router.beforeEach((to) => {
    const auth = useAuthStore();
    if(to.meta.auth && !auth.isLoggedIn) return { name: 'login', query: { redirect: to.fullPath }}
})

export default router;