<script setup>

import {ref, watch} from "vue";
import {useNotifyStore} from "@/stores/app.js";
import {createProduct} from "@/api/productApi.js";
import {createCustomer} from "@/api/customerApi.js";


const emit = defineEmits(['cancelEdit', 'refreshAllCustomers'])

const customer = ref({
  id: 0,
  firstName: "",
  lastName: "",
  email: "",
  community: false
})

const valid = ref(false)

// einfache Regeln
const rules = {
  required: v => !!v || 'Pflichtfeld',
  positive: v => v > 0 || 'Muss > 0 sein',
  nonNegative: v => v >= 0 || 'Muss ≥ 0 sein',
  containerSet: v => product.value.container.stock > 0 && product.value.container.capacity > 0 || 'Setze zuerst Kapazität und Bestand des Kontainers',
  email: v => /.+@.+\..+/.test(v) || 'Ungültige E-Mail'
}

const submit = async () => {
  useNotifyStore().set('Der Benutzer wurde erstellt.')
  await createCustomer(customer.value)

  emit('refreshAllCustomers')
  emit("cancelEdit")
}


</script>

<template>
  <v-card>
    <v-row>
      <v-col cols="12" sm="9">
        <v-card-title>Erstelle einen neuen Benutzer</v-card-title>
      </v-col>
      <v-col
        class="d-flex justify-md-end justify-sm-center"
        cols="12"
        sm="3">
        <div class="pr-2 d-flex align-center pb-8">
          <v-btn color="primary" icon="mdi-close" @click="emit('cancelEdit')"></v-btn>
        </div>
      </v-col>
    </v-row>
    <v-divider></v-divider>
    <v-card-actions>
      <v-row>
        <v-col cols="12" md="12">
          <v-card class="pa-4 h-100">

            <v-form ref="form" v-model="valid">

              <v-text-field
                v-model="customer.firstName"
                label="Vorname"
                :rules="[rules.required]"
              />

              <v-text-field
                v-model="customer.lastName"
                label="Nachname"
                :rules="[rules.required]"
              />

              <v-text-field
                v-model="customer.email"
                label="E-Mail"
                :rules="[rules.required, rules.email]"
              />

              <v-switch
                v-model="customer.community"
                label="Community Profil"
                color="primary"
              />

            </v-form>
          </v-card>
        </v-col>
      </v-row>
    </v-card-actions>
    <v-card-actions>
      <v-btn @click="submit" :disabled="!valid" color="primary" block border>Speichern</v-btn>
    </v-card-actions>
  </v-card>
</template>

<style scoped>

</style>
