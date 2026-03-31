<script setup>

import {ref} from "vue";
import {useNotifyStore} from "@/stores/app.js";
import {updateProduct} from "@/api/productApi.js";


const emit = defineEmits(['cancelEdit', 'submitNewProduct'])

const props = defineProps({
  product: {
    type: Object,
    required: true,
    default: {
      id: 0,
      name: "NOT AVAILABLE",
      description: "NOT AVAILABLE",
      price: 0.00,
      category:"NOT AVAILABLE",
      stock: 1,
      container: {}
    }
  }
})

const valid = ref(false)

const categories = [
  'NON_ALCOHOL',
  'ALCOHOL',
  'SNACK'
]

// einfache Regeln
const rules = {
  required: v => !!v || 'Pflichtfeld',
  positive: v => v > 0 || 'Muss > 0 sein',
  nonNegative: v => v >= 0 || 'Muss ≥ 0 sein',
}

const submit = () => {
  useNotifyStore().set('Das Produkt wurde aktualisiert')
  updateProduct(props.product)
  emit("cancelEdit")
}
</script>

<template>
<v-card>
  <v-row>
    <v-col cols="12" sm="3">
      <v-card-title>{{product.name}}</v-card-title>
      <v-card-subtitle>{{product.description}}</v-card-subtitle>
    </v-col>
    <v-col cols="12" sm="6">
      <v-card-title>Preis: {{product.price.toFixed(2)}} €</v-card-title>
      <v-card-subtitle>{{product.category}}</v-card-subtitle>
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
    <v-card class="pa-4 flex-grow-1">

      <v-form ref="form" v-model="valid">

        <v-text-field
          v-model="product.name"
          label="Name"
          :rules="[rules.required]"
        />

        <v-text-field
          v-model="product.description"
          label="Beschreibung"
        />

        <v-text-field
          v-model.number="product.price"
          label="Preis (€)"
          type="number"
          :rules="[rules.required, rules.positive]"
        />

        <v-select
          v-model="product.category"
          :items="categories"
          label="Kategorie"
          :rules="[rules.required]"
        />

        <v-text-field
          v-model.number="product.stock"
          label="Bestand"
          type="number"
          :rules="[rules.required, rules.nonNegative]"
        />

        <v-btn
          class="mt-4"
          color="primary"
          :disabled="!valid"
          border
          @click="submit"
        >
          Speichern
        </v-btn>

      </v-form>
    </v-card>
  </v-card-actions>
</v-card>
</template>

<style scoped>

</style>
