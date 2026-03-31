<script setup>

import {ref, watch} from "vue";
import {useNotifyStore} from "@/stores/app.js";
import {createProduct} from "@/api/productApi.js";


const emit = defineEmits(['cancelEdit'])

const product = ref({
  id: 0,
  name: "",
  description: "",
  price: 0,
  category:"",
  stock: "",
  container: {}
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
  useNotifyStore().set('Das Produkt wurde erstellt.')
  createProduct(product.value)
  emit("cancelEdit")
}

watch(
  () => [product.value.name, product.value.description, product.value.category],
  ([newName, newDescription, newCategory]) => {
    product.value.container.name = newName
    product.value.container.description = newDescription
    product.value.container.category = newCategory
  }
)
</script>

<template>
  <v-card>
    <v-row>
      <v-col cols="12" sm="9">
        <v-card-title>Erstelle ein neues Produkt</v-card-title>
        <v-card-subtitle>und den Container in dem das Produkt aufbewahrt wird.</v-card-subtitle>
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
      <v-row class="w-100">

        <!-- 🧾 Produkt Formular -->
        <v-col cols="12" md="6">
          <v-card class="pa-4 h-100">
            <v-card-title>Produkt</v-card-title>

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
                label="Bestand Stk. einzeln"
                type="number"
                :rules="[rules.required, rules.nonNegative]"
              />

            </v-form>
          </v-card>
        </v-col>

        <!-- 📦 Container Formular -->
        <v-col cols="12" md="6">
          <v-card class="pa-4 h-100">

            <v-card-title>Kontainer</v-card-title>

            <v-text-field
              v-model="product.container.name"
              label="Container Name"
              :rules="[rules.required]"
            />

            <v-text-field
              v-model="product.container.description"
              label="Beschreibung"
            />

            <v-select
              v-model="product.container.category"
              :items="categories"
              label="Kategorie"
              :rules="[rules.required]"
            />

            <v-text-field
              v-model.number="product.container.capacity"
              label="Kapazität"
              type="number"
              :rules="[rules.required, rules.positive]"
            />

            <v-text-field
              v-model.number="product.container.stock"

              label="Bestand aller Kontainer"
              type="number"
              :rules="[rules.required, rules.nonNegative]"
            />

          </v-card>
        </v-col>

      </v-row>
    </v-card-actions>
    <v-card-actions><v-btn @click="submit" color="primary" block border>Speichern</v-btn></v-card-actions>
  </v-card>
</template>

<style scoped>

</style>
