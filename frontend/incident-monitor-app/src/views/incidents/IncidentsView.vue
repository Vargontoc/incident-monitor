<template>
    <div class="card">
        <div class="filters">
            <input v-model="title" placeholder="Buscar por titulo" @keyup.enter="load"/>
            <button class="btn btn-primary" @click="load">Buscar</button>
        </div>
        <router-link to="/incidents/new">Nueva incidentcia</router-link>


        <div v-if="loading">Cargando...</div>
        <table v-else class="table">
            <thead>
                <tr>
                    <th>Titlulo</th>
                    <th>Estado</th>
                    <th>Prioridad</th>
                    <th>Creada</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="r in rows" :key="r.id" >
                    <td style="cursor: pointer" @click="$router.push(`/incidents/${r.id}`)">{{ r.title }}</td>
                    <td>
                        <span class="badge">
                            {{ r.status }}
                        </span>
                    </td>
                    <td>
                        <span class="badge">
                            {{ r.priority }}
                        </span>
                    </td>
                    <td>{{ new Date(r.createdAt || '').toLocaleString() }}</td>
                    <td style="text-align: right;">
                        <button class="btn btn-ghost" @click="$router.push(`/incidents/${r.id}/edit`)">Editar</button>
                    </td>
                </tr>
            </tbody>
        </table>

        <div class="pager" v-if="loading === false">
            <button :disabled="page === 0" class="btn btn-ghost" @click="prev"><< Anterior</button>
            <span>Página {{  page + 1 }} / {{ totalPages }}</span>
            <button :disabled="page + 1 >= totalPages" class="btn btn-ghost" @click="next">Siguiente >></button>
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