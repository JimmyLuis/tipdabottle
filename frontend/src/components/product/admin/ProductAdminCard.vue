<script setup>

import {ref} from "vue";
import {useNotifyStore} from "@/stores/app.js";
import {updateProduct} from "@/api/productApi.js";
import BasicDialogCard from "@/components/common/card/BasicDialogCard.vue";
import CloseBtn from "@/components/common/card/actions/CloseBtn.vue";


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
  <BasicDialogCard>
    <template #header>
      <v-row>
        <v-col cols="12" sm="6">
          <v-card-title>{{product.name}}</v-card-title>
          <v-card-subtitle>{{product.description}}</v-card-subtitle>
        </v-col>
        <v-col cols="12" sm="6">
          <v-card-title>Preis: {{product.price.toFixed(2)}} €</v-card-title>
          <v-card-subtitle>{{product.category}}</v-card-subtitle>
        </v-col>

      </v-row>
    </template>
    <template #header-actions>
      <div class="d-flex justify-end">
        <CloseBtn @close="emit('cancelEdit')"/>
      </div>
    </template>
    <template #body>
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
    </template>
  </BasicDialogCard>

</template>

<style scoped>

</style>
