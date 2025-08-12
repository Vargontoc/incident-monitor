<template>
    <div>
        <h1>{{ id ?  'Editar Incicencia' : 'Nueva incidencia' }}</h1>
        <form @submit.prevent="save">
            <input  v-model="title" placeholder="Titulo" required maxlength="160"/>
            <textarea v-model="description" placeholder="Descripción" rows="5"></textarea>
            <select v-model="priority">
                <option v-for="(p, i) in priorities" :key="p +  '-' + i">{{ p }}</option>
            </select>
            <button :disabled="loading">{{ loading ? ' Guardando...': 'Guardar' }}</button>
        </form>
    </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { createIncident, getIncident, updateIncident } from '../../services/IncidentService';

const route = useRoute();
const router = useRouter();
const id = route.params.id as string | undefined;
const title = ref('');
const description = ref('');
const priority = ref<'HIGH' | 'LOW' |  'MEDIUM' | 'CRITICAL'>('HIGH');
const priorities = ['HIGH','LOW',  'MEDIUM', 'CRITICAL']
const loading = ref(false);

onMounted(async () => {
    if(id) {
        const it = await getIncident(id);
        title.value = it.title;
        description.value = it.description || '';
        priority.value = it.priority;
    }
})

async function save() {
    loading.value = true;

    try {
        if(!id) {
            await createIncident({ title: title.value, description: description.value || '', priority: priority.value});
        }else {
            await updateIncident(id, { title: title.value, description: description.value || '', priority: priority.value});
        }
        router.push('/incidents');
    }finally{ loading.value = false; }
}
</script>