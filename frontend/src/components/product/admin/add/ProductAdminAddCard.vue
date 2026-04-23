<script setup>

import {ref, watch} from "vue";
import {useNotifyStore} from "@/stores/app.js";
import {createProduct} from "@/api/productApi.js";
import BasicDialogCard from "@/components/common/card/BasicDialogCard.vue";
import CloseBtn from "@/components/common/card/actions/CloseBtn.vue";


const emit = defineEmits(['cancelEdit', 'refreshAllProducts'])

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
  containerSet: v => product.value.container.stock > 0 && product.value.container.capacity > 0 || 'Setze zuerst Kapazität und Bestand des Kontainers'
}

const submit = async () => {
  useNotifyStore().set('Das Produkt wurde erstellt.')
  await createProduct(product.value)
  emit('refreshAllProducts')
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
watch(() => [product.value.container.capacity, product.value.container.stock, product.value.stock],
  ([newContainerCap, newContainerStock, newProductStock], [oldContainerCap, oldContainerStock, oldProductStock]) => {
    if (oldContainerCap !== newContainerCap){
      product.value.container.stock = Math.ceil(product.value.stock / newContainerCap)
    }
    if (oldContainerStock !== newContainerStock){
      product.value.stock = product.value.container.capacity * newContainerStock
    }
  }
)


</script>

<template>
  <BasicDialogCard>
    <template #header>
      <v-card-title>Erstelle ein neues Produkt</v-card-title>
      <v-card-subtitle>und den Container in dem das Produkt aufbewahrt wird.</v-card-subtitle>
    </template>
    <template #header-actions>
      <div class="justify-end d-flex">
        <CloseBtn @close="emit('cancelEdit')"/>
      </div>
    </template>
    <template #body>
      <v-row class="pb-3">

        <!-- 🧾 Produkt Formular -->
        <v-col cols="12" md="6">
          <v-card class="pa-4" border elevation="0">
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
                disabled
                :rules="[rules.required, rules.nonNegative, rules.containerSet]"
              />

            </v-form>
          </v-card>
        </v-col>

        <!-- 📦 Container Formular -->
        <v-col cols="12" md="6">
          <v-card class="pa-4" elevation="0" border>

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
        <v-card-actions  class="d-flex flex-grow-1 ma-1">
          <v-btn  @click="submit" :disabled="!valid" color="primary" block class="border">Speichern</v-btn>
        </v-card-actions>

      </v-row>

    </template>
  </BasicDialogCard>

</template>

<style scoped>

</style>
