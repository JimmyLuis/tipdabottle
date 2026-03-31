<script setup>

import {useErrorStore, useNotifyStore} from "@/stores/app.js";
import {storeToRefs} from "pinia";
import {ref} from "vue";

const notifyStore = useNotifyStore()
const message = storeToRefs(notifyStore)
</script>

<template>
  <v-snackbar
    color="info"
    timeout="3000"
    v-model="notifyStore.show"
    timer="top"
    location="top left"
    z-index="2500"
  >
    <h3>Info</h3>
    {{message.message}}
  </v-snackbar>
</template>

<style scoped>

</style>
