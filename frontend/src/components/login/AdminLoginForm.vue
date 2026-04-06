<script setup>
import { ref } from 'vue'
import {useAuthStore, useErrorStore} from "@/stores/app.js";
import router from "@/router/index.js";


const auth = useAuthStore()

const form = ref(null)
const valid = ref(false)

const username = ref('')
const password = ref('')
const visible = ref(false)

const rules = {
  required: (v) => !!v || 'Pflichtfeld'
}

const handleLogin = async () => {
  const { valid } = await form.value.validate()

  if (!valid) return

  try {
    await auth.login(username.value, password.value)
    if (useAuthStore().isAuthenticated){
      router.push("/")
    }
  } catch (e) {
    useErrorStore().set('Login fehlgeschlagen')
  }
}
</script>

<template>
  <v-container fluid class="fill-height d-flex align-center justify-center">
    <v-form ref="form" v-model="valid" @submit.prevent="handleLogin">
      <v-card
        class="mx-auto pa-12 pb-8"
        elevation="3"
        rounded="lg"
      >
        <div class="text-body-large text-medium-emphasis">Benutzer</div>

        <v-text-field
          v-model="username"
          :rules="[rules.required]"
          density="compact"
          placeholder="Nutzername"
          prepend-inner-icon="mdi-account-outline"
          variant="outlined"
        />

        <div class="text-body-large text-medium-emphasis mt-4">
          Passwort
        </div>

        <v-text-field
          v-model="password"
          :rules="[rules.required]"
          :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
          :type="visible ? 'text' : 'password'"
          density="compact"
          placeholder="Passwort Eingabe"
          prepend-inner-icon="mdi-lock-outline"
          variant="outlined"
          @click:append-inner="visible = !visible"
        />

        <v-btn
          class="mt-6"
          color="primary"
          size="large"
          variant="tonal"
          block
          type="submit"
        >
          Autorisieren
        </v-btn>
      </v-card>
    </v-form>
  </v-container>
</template>

<style scoped>

</style>
