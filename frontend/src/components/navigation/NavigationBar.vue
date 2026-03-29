<script setup>

import router from "@/router/index.js";
import AdminPanelDialog from "@/components/admin/AdminPanelDialog.vue";
</script>

<template>
  <v-app-bar class="rounded-b-lg" :elevation="10" color="red-darken-2" height="75">

    <v-app-bar-title class="font-weight-bold pl-5">
      <div @click="router.push('/')">
        tipdabottle
      </div>
      <v-spacer></v-spacer>
    </v-app-bar-title>

    <template v-slot:append>
      <v-btn class="rounded-xl mr-3" variant="outlined" @click="router.push('/purchase-history')">
        <span class="pr-2 d-none d-sm-inline">Bestellhistorie</span>
        <v-icon size="25" icon="mdi-receipt-text-clock-outline"></v-icon>
      </v-btn>
      <AdminPanelDialog/>
    </template>
  </v-app-bar>
</template>

<style scoped>

</style>
