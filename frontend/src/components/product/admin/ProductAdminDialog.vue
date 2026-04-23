<script setup>
import {ref, watch} from "vue";
import { useNotifyValidationStore} from "@/stores/app.js";
import ProductAdminCard from "@/components/product/admin/ProductAdminCard.vue";
import AdminSlotRefactor from "@/components/product/admin/AdminSlotRefactor.vue";

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

const emit = defineEmits(['refreshCustomer'])

const isActive = ref(false);

</script>

<template>
  <v-dialog v-model="isActive">
    <template v-slot:activator="{props: activatorProps}">
      <AdminSlotRefactor v-bind="activatorProps" :product/>
    </template>
    <template v-slot:default>
      <ProductAdminCard :product @cancel-edit="isActive = false" @submit-new-product=""/>
    </template>
  </v-dialog>
</template>

<style scoped>

</style>
