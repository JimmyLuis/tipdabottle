<script setup>
import {ref} from "vue";
import ProductAdminAddCard from "@/components/product/admin/add/ProductAdminAddCard.vue";

const emit = defineEmits(['refreshAllProducts'])

const isActive = ref(false);


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
          <ProductAdminAddCard @cancel-edit="isActive= false" @refresh-all-products="emit('refreshAllProducts')"/>
        </v-card-item>
      </v-card>
    </template>
  </v-dialog>
</template>

<style scoped>

</style>
