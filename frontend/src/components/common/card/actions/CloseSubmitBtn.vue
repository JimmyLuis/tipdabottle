<script setup>
import CloseBtn from "@/components/common/card/actions/CloseBtn.vue";
import SubmitBtn from "@/components/common/card/actions/SubmitBtn.vue";

const emit = defineEmits(['close', 'submit'])
</script>

<template>
  <div class="d-flex">
    <CloseBtn @close="emit('close')"/>
    <v-divider vertical thickness="1" opacity="0.2" class="mx-3"/>
    <SubmitBtn @submit="emit('submit')"/>
  </div>
</template>

<style scoped>

</style>
