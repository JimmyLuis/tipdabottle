<script setup>
import { ref } from 'vue'
import {updateCustomerBalance} from "@/api/customerApi.js";
import {useNotifyStore} from "@/stores/app.js";

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

const form = ref(null)
const isValid = ref(false)

// Betrag kann positiv oder negativ sein
const amount = ref(null)
const reason = ref('')

// Validation Regeln
const rules = {
  required: v => v !== null && v !== '' || 'Pflichtfeld',
  number: v => !isNaN(parseFloat(v)) || 'Muss eine Zahl sein'
}

const submit = async () => {
  const { valid } = await form.value.validate()
  if (!valid) return
  await updateCustomerBalance(props.customer.id, parseFloat(amount.value));
  useNotifyStore().set('Saldo geändert!')

}
</script>
<template>
  <v-card class="pa-4" max-width="400">
    <v-card-title>Saldo anpassen</v-card-title>

    <v-form ref="form" v-model="isValid">
      <v-text-field
        v-model="amount"
        label="Betrag (+ / -)"
        type="number"
        :rules="[rules.required, rules.number]"
        prefix="€"
      />

      <v-textarea
        v-model="reason"
        label="Grund (optional)"
        rows="2"
      />

      <v-btn
        color="red"
        class="mt-4"
        :disabled="!isValid"
        @click="submit"
      >
        Anwenden
      </v-btn>
    </v-form>
  </v-card>
</template>


