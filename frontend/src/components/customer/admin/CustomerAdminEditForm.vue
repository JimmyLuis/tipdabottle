<script setup>
import { ref } from 'vue'
import CustomerAdminBalanceForm from "@/components/customer/admin/CustomerAdminBalanceForm.vue";
import {updateCustomer} from "@/api/customerApi.js";
import {useNotifyStore} from "@/stores/app.js";

const form = ref(null)
const isValid = ref(false)

const props = defineProps({
  customer: {
    type: Object,
    required: true,
    default: {
      firstName: "NOT AVAILABLE",
      lastName: "NOT AVAILABLE",
      balance: 0.00
    }
  }
})

// Validation Regeln
const rules = {
  required: v => !!v || 'Pflichtfeld',
  email: v => /.+@.+\..+/.test(v) || 'Ungültige E-Mail'
}

const save = async () => {
  const { valid } = await form.value.validate()

  if (!valid) return

  useNotifyStore().set('Änderungen gespeichert!')
  await updateCustomer(props.customer)
}
</script>

<template>
  <v-container fluid>
    <v-row >
      <v-col cols="12" sm="8" class="d-flex flex-column justify-center">
        <v-card class="pa-4" >
          <v-card-title>User bearbeiten</v-card-title>

          <v-form ref="form" v-model="isValid">
            <v-row>
              <v-col cols="12" sm="7">
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
                  type="email"
                  :rules="[rules.required, rules.email]"
                />
              </v-col>
              <v-col cols="12" sm="5">
                <v-switch
                  v-model="customer.community"
                  label="Community Profil"
                />

                <v-switch
                  v-model="customer.activeProfile"
                  label="Profil aktiv"
                />

                <v-btn
                  color="primary"
                  class="mt-4"
                  :disabled="!isValid"
                  @click="save"
                >
                  Speichern
                </v-btn>
              </v-col>
            </v-row>
          </v-form>
        </v-card>
      </v-col>
      <v-col cols="12" sm="4" class="d-flex flex-column justify-center">
        <CustomerAdminBalanceForm :customer/>
      </v-col>
    </v-row>
  </v-container>
</template>

