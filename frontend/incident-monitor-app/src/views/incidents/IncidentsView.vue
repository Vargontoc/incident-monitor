<template>
    <div>
        <h1>Incidencias</h1>
        <div class="filters">
            <input v-model="title" placeholder="Buscar por titulo" @keyup.enter="load"/>
            <label>
                <input v-model="mine" type="checkbox" @change="load" /> Creadas por mi
            </label>
            <label>
                <input v-model="assignedToMe" type="checkbox" @change="load" /> Asignadas a mi
            </label>
            <select multiple v-model="statues">
                <option v-for="(s, i) in ['OPEN','IN_PROGRESS','RESOLVED','CLOSED']" :key="s + '-' + i" :value="s">{{ s }}</option>
            </select>
            <select multiple v-model="priorities" >
                <option v-for="(s, i) in ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL']" :key="s + '-' + i" :value="s">{{ s }}</option>
            </select>
            <button @click="load">Buscar</button>
            <router-link to="/incidents/new">Nueva incidentcia</router-link>
            
        </div>

        <div v-if="loading">Cargando...</div>
        <table v-else>
            <thead>
                <tr>
                    <th>Titlulo</th>
                    <th>Estado</th>
                    <th>Prioridad</th>
                    <th>Creada</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="r in rows" :key="r.id" style="cursor: pointer" @click="$router.push(`/incidents/${r.id}`)">
                    <td>{{ r.title }}</td>
                    <td>{{ r.status }}</td>
                    <td>{{ r.priority }}</td>
                    <td>{{ new Date(r.createdAt || '').toLocaleString() }}</td>
                </tr>
            </tbody>
        </table>

        <div class="pager" style="margin-top:8px; display: flex; gap:8px; align-items: center;">
            <button :disabled="page === 0" @click="prev"><< Anterior</button>
            <span>Página {{  page + 1 }} / {{ totalPages }}</span>
            <button :disabled="page + 1 >= totalPages" @click="next">Siguiente >></button>
        </div>
    </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import type { Incident } from '../../models/Incident';
import { getIncidents } from '../../services/IncidentService';

const rows = ref<Incident[]>([])
const loading = ref(false)
const page = ref(0)
const size = ref(10)
const total = ref(0)

const title = ref('')
const mine = ref(false)
const assignedToMe = ref(false)
const statues = ref<[]>([])
const priorities = ref<[]>([]);
const totalPages = computed(() => Math.ceil(total.value / size.value));

function prev() {
    if(page.value > 0) {
        page.value--;
        load();
    }
}

function next()
{
    if(page.value + 1 < totalPages.value) {
        page.value++;
        load();
    }
}

async function load() {
    loading.value = true;
    try {
        const res = await getIncidents({
            page: page.value,
            size: size.value,
            mine: mine.value,
            title: title.value || undefined,
            sort: 'createdAt,desc',
            statues: statues.value || [],
            priorities: priorities.value || [],
            assignedToMe: assignedToMe.value
        });
        rows.value = res.content;
        total.value = res.totalElements
        size.value = res.size;
        page.value = res.number;
    }finally {
        loading.value = false;
    }
}

onMounted(load)
</script>