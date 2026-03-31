<script setup>

import {useErrorStore} from "@/stores/app.js";
import {storeToRefs} from "pinia";
import {ref} from "vue";

const errorStore = useErrorStore()
const message = storeToRefs(errorStore)
</script>

<template>
  <v-snackbar
    color="error"
    timeout="10000"
    v-model="errorStore.show"
    timer="top"
    location="top left"
    z-index="2500"
  >
    <h3>Ooops, da ist wohl etwas schief gelaufen...</h3>
    {{message.message}}
  </v-snackbar>
</template>

<style scoped>

</style>
