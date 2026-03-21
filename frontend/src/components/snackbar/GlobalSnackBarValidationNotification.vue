<script setup>

import {useErrorStore, useNotifyStore, useNotifyValidationStore} from "@/stores/app.js";
import {storeToRefs} from "pinia";
import {ref} from "vue";

const notifyValidationStore = useNotifyValidationStore()
const message = storeToRefs(notifyValidationStore)
</script>

<template>
  <v-snackbar
    color="warning"
    timeout="5000"
    v-model="notifyValidationStore.show"
    timer="top"
    transition="slide-x-transition"
    location="top right"
    z-index="2500"
  >
    <h3>Achtung</h3>
    {{message.message}}
  </v-snackbar>
</template>

<style scoped>

</style>
