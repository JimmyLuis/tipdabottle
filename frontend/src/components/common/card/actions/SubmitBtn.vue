<script setup>
const emit = defineEmits(['submit'])
</script>

<template>
  <v-btn color="secondary" icon="mdi-arrow-right" @click="emit('submit')"></v-btn>
</template>

<style scoped>

</style>
