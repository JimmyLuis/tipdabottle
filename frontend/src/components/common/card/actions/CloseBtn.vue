<script setup>
const emit = defineEmits(['close'])
</script>

<template>
  <v-btn color="primary" icon="mdi-close" @click="emit('close')"></v-btn>
</template>

<style scoped>

</style>
