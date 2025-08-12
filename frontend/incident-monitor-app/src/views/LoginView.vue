<template>
    <div class="container">
        <h1>Incident Monitor - Login</h1>
        <form @submit.prevent="submit">
            <input v-model="username" placeholder="Usuario" type="text" required />
            <input v-model="password" placeholder="Contraseña" type="password" required />
            <button :disabled="loading">{{ loading ? 'Iniciando sesion...' : 'Iniciar sesion' }}</button>
            <p v-if="error" style="color: red">{{ error }}</p>
        </form>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useAuthStore } from '../stores/auth';
import { login } from '../services/auth';


const username = ref('');
const password = ref('');
const loading = ref(false);
const error = ref('');
const router = useRouter();
const route = useRoute();
const auth = useAuthStore();

async function submit() {
    try {
        loading.value = true; error.value = '';
        const tokens = await login(username.value, password.value);
        auth.setTokens(tokens);
        router.push((route.query.redirect as string) || '/incidents')
    }catch(e: any) {
        error.value = e?.response?.data?.detail || 'Login failed';
    }finally {
        loading.value = false
    }
}
</script>