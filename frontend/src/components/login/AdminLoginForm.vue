<script setup>
import { ref } from 'vue'

const visible = ref(false)
</script>

<template>
  <div>
    <v-card
      class="mx-auto pa-12 pb-8"
      elevation="3"
      max-width="448"
      rounded="lg"
    >
      <div class="text-body-large text-medium-emphasis">Benutzer</div>

      <v-text-field
        density="compact"
        placeholder="Nutzername"
        prepend-inner-icon="mdi-account-outline"
        variant="outlined"
      ></v-text-field>

      <div class="text-body-large text-medium-emphasis d-flex align-center justify-space-between">
        Passwort

      </div>

      <v-text-field
        :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
        :type="visible ? 'text' : 'password'"
        density="compact"
        placeholder="Passwort Eingabe"
        prepend-inner-icon="mdi-lock-outline"
        variant="outlined"
        @click:append-inner="visible = !visible"
      ></v-text-field>

      <v-card
        class="mb-12"
        color="surface-variant"
        variant="tonal"
      >
      </v-card>

      <v-btn
        class="mb-8"
        color="primary"
        size="large"
        variant="tonal"
        block
      >
        Autorisieren
      </v-btn>
    </v-card>
  </div>
</template>

<style scoped>

</style>
