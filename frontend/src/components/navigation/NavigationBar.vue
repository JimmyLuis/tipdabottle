<script setup>

import router from "@/router/index.js";
</script>

<template>
  <v-app-bar class="rounded-b-lg" :elevation="10" color="red-darken-2" height="75">

    <v-app-bar-title class="font-weight-bold pl-5" @click="router.push('/')">tipdabottle</v-app-bar-title>

    <template v-slot:append>
      <v-btn class="border rounded-xl mr-3" @click="router.push('/purchase-history')">
        <span class="pr-2">Bestellhistorie</span>
        <v-icon size="25" icon="mdi-receipt-text-clock-outline"></v-icon>
      </v-btn>
      <v-btn icon="mdi-account-cog-outline"/>
    </template>
  </v-app-bar>
</template>

<style scoped>

</style>
