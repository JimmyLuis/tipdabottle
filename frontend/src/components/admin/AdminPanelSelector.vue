<script setup>

import router from "@/router/index.js";
import {useAuthStore} from "@/stores/app.js";

const emit = defineEmits(['closePanel'])

const handleSelection = (route) => {
  router.push(route);
  emit('closePanel')
}

async function logout() {
  await useAuthStore().logout()
  window.location.reload()
}
</script>

<template>
<v-container class="d-flex justify-center align-center flex-grow-0" >
  <v-card width="700">
    <v-card-title class="align-center justify-center d-flex mt-3 mb-0">
      <v-row>
        <v-col cols="12" sm="10">
          <div class="text-h4 font-weight-medium">Verwaltung</div>
        </v-col>
        <v-col cols="12" sm="2" class="d-flex justify-end">
          <v-btn color="primary" @click="emit('closePanel')" icon="mdi-close"></v-btn>
        </v-col>
      </v-row>
    </v-card-title>
    <v-card-actions class="d-inline">
      <v-divider thickness="3" class="mx-2 mb-5"></v-divider>
      <v-row>
        <v-col cols="12" sm="4" class="d-flex justify-center align-center">
          <div class="d-flex flex-column align-center text-center">
            <v-btn @click="handleSelection('/customer-cp')" border="md" rounded="circle" size="80" color="primary">
              <v-icon icon="mdi-account-eye" size="60"></v-icon>
            </v-btn>
            <div class="pt-2 text-h6">Nutzer Verwalten</div>
          </div>
        </v-col>
        <v-col cols="12" sm="4" class="d-flex justify-center align-center">
          <div class="d-flex flex-column align-center text-center">
            <v-btn @click="handleSelection('/product-cp')" border="md" rounded="circle" size="80" color="primary">
              <v-icon icon="mdi-store-edit" size="60"></v-icon>
            </v-btn>
            <div class="pt-2 text-h6">Produkte Verwalten</div>
          </div>
        </v-col>
        <v-col cols="12" sm="4" class="d-flex justify-center align-center">
          <div class="d-flex flex-column align-center text-center">
            <v-btn border="md" rounded="circle" size="80" color="primary" @click="logout()">
              <v-icon icon="mdi-exit-to-app" size="60"></v-icon>
            </v-btn>
            <div class="pt-2 text-h6">Abmelden</div>
          </div>
        </v-col>
      </v-row>
    </v-card-actions>
  </v-card>
</v-container>
</template>

<style scoped>

</style>
