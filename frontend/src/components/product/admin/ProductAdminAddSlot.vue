<script setup>
import {purchaseProducts} from "@/api/purchaseApi.js";
import {ref} from "vue";
import {useNotifyStore} from "@/stores/app.js";
import ProductAdminCard from "@/components/product/admin/ProductAdminCard.vue";
import ProductAdminAddCard from "@/components/product/admin/ProductAdminAddCard.vue";

const props = defineProps({
  product: {
    type: Object,
    required: true,
    default: {
      id: 0,
      name: "Neuer Name",
      description: "Neue Beschreibung",
      price: 0.00,
      category:"SNACK",
      stock: 1,
      container: {}
    }
  }
})

const notify = useNotifyStore()

const emit = defineEmits(['refreshProduct'])

const isActive = ref(false);


const submitNewProduct = async function(product) {
  notify.set(`Das Produkt wurde hinzugefügt!`)
  isActive.value = false

}

</script>

<template>
  <v-dialog v-model="isActive">
    <template v-slot:activator="{props: activatorProps}">
      <v-sheet v-bind="activatorProps" class="ma-2 pa-2">
        <v-btn color="secondary" block height="40">
          <v-icon icon="mdi-arrow-right"></v-icon>
          <span class="px-5">Neues Produkt Hinzufügen</span>
          <v-icon icon="mdi-arrow-left"></v-icon>
        </v-btn>
      </v-sheet>
    </template>
    <template v-slot:default>
      <v-card height="700" >
        <v-card-item >
          <ProductAdminAddCard @cancel-edit="isActive= !isActive" @submit-new-product="submitNewProduct"/>
        </v-card-item>
      </v-card>
    </template>
  </v-dialog>
</template>

<style scoped>

</style>
