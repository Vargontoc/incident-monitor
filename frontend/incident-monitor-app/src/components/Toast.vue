<template>
    <div v-if="t.open" :style="`position:fixed;right:12px;bottom:12px;padding:10px;border-radius:8px;background:${t.type==='error'?'#ffdddd':'#ddffdd'};`">
        {{ t.msg }}
    </div>
</template>
<script setup lang="ts">
import { useToast } from '../stores/toast';
const t = useToast();
</script>