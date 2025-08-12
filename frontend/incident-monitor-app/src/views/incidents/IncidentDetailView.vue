<template>
    <div>
        <h1>{{ incident?.title }}</h1>
        <p><b>Estado:</b>{{ incident?.status }} | <b>Prioridad</b> {{ incident?.priority }}</p>
        <p>{{ incident?.description }}</p>
        <button @click="getSuggestion">💡 Sugerir acción</button>


        <h2>Comentarios</h2>

        <ul>
            <li v-for="c in comments" :key="c.id">
                <div>{{ c.content }}</div>
                <small>{{ new Date(c.createdAt).toLocaleString() }}</small>
            </li>
        </ul>

        <form @submit.prevent="postComment">
            <textarea v-model="newComment"  placeholder="Añadir comentario" rows="5"></textarea>
            <button>Enviar</button>
        </form>

        <div v-if="modalOpen" style="background-color: black; position: fixed; inset: 0; display: flex; align-items: center; justify-content: center;">
            <div style="background: white; padding: 1rem; max-width: 600px; width: 90%; color: black ;">
                <h3>Sugerencias</h3>
                <div v-if="suggesting">Consultando IA...</div>
                <ul v-else>
                    <li v-for="s in suggestion?.suggestions || []" :key="s">{{ s }}</li>
                </ul>
                <p v-if="suggestion?.reasoning"><i>{{ suggestion?.reasoning }}</i></p>
                <button @click="modalOpen = false">Cerrar</button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router';
import type { Incident } from '../../models/Incident';
import { onMounted, ref } from 'vue';
import type { IncidentComment } from '../../models/IncidentComment';
import type { SuggestResponse } from '../../models/SuggestResponse';
import { getIncident, suggest } from '../../services/IncidentService';
import { addComment, getComments } from '../../services/IncidentCommentsService';

const route = useRoute();
const id= route.params.id as string;
const incident = ref<Incident | null>(null);
const comments = ref<IncidentComment[]>([]);
const newComment = ref('');
const loading = ref(false);
const suggesting = ref(false);
const modalOpen = ref(false);
const suggestion = ref<SuggestResponse | null>(null);

async function load() {
    loading.value = true;
    try {
        incident.value = await getIncident(id);
        comments.value = await getComments(id);
    }finally{ loading.value = false;}
}

async function postComment() {
    if(!newComment.value.trim()) return;
    await addComment(id, newComment.value.trim())
    newComment.value = '';
    comments.value = await getComments(id);
}

async function getSuggestion() {
    suggesting.value = true;
    suggestion.value = null;
    modalOpen.value = true;
    try {
        suggestion.value = await suggest(id, []);
    }catch(e) {
        suggestion.value = { suggestions: ['No disponble'], reasoning: 'Error al llamar a IA'};
    }finally { suggesting.value = false; }
}
onMounted(load);
</script>