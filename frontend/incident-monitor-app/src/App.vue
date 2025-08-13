<script setup lang="ts">
import { RouterView } from 'vue-router';
import Toast from './components/Toast.vue';
import { onMounted, ref } from 'vue';

const theme = ref<'light' | 'dark'>('light');

onMounted(() => {
  const saved = localStorage.getItem('theme');
  if(saved === 'dark' || (saved === null && matchMedia('(prefers-color-scheme: dark)').matches)) {
    theme.value = 'dark'
  }

  document.documentElement.setAttribute('data-theme', theme.value);
})

function toggleTheme() {
  theme.value = theme.value === 'dark' ? 'light' : 'dark';
  document.documentElement.setAttribute('data-theme', theme.value);
  localStorage.setItem('theme', theme.value)
}
</script>

<template>
<div>
  <header class="appbar">
    <div class="brand">Incident Monitor</div>
    <div style="display: flex; gap: .5rem; align-items: center;" >
      <button class="btn btn-ghost" @click="toggleTheme">
         {{ theme === 'dark' ? '🌙' : '☀️' }}
      </button>
    </div>
  </header>
  <main class="container">
    <RouterView />
    <toast />
  </main>
</div>
</template>

