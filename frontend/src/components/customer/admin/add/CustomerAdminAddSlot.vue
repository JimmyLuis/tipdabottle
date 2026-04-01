<script setup>
import {ref} from "vue";
import ProductAdminAddCard from "@/components/product/admin/add/ProductAdminAddCard.vue";
import CustomerAdminAddCard from "@/components/customer/admin/add/CustomerAdminAddCard.vue";

const emit = defineEmits(['refreshAllCustomers'])

const isActive = ref(false);


</script>

<template>
  <v-dialog v-model="isActive">
    <template v-slot:activator="{props: activatorProps}">
      <v-sheet v-bind="activatorProps" class="ma-2 pa-2">
        <v-btn color="secondary" block height="40">
          <v-icon icon="mdi-arrow-right"></v-icon>
          <span class="px-5">Neuen Benutzer Hinzufügen</span>
          <v-icon icon="mdi-arrow-left"></v-icon>
        </v-btn>
      </v-sheet>
    </template>
    <template v-slot:default>
      <v-card height="550" >
        <v-card-item >
          <CustomerAdminAddCard @cancel-edit="isActive= false" @refresh-all-customers="emit('refreshAllCustomers')"/>
        </v-card-item>
      </v-card>
    </template>
  </v-dialog>
</template>

<style scoped>

</style>
