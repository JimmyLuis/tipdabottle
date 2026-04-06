<script setup>
import {ref} from "vue";
import CustomerAdminCard from "@/components/customer/admin/CustomerAdminCard.vue";

const props = defineProps({
  customer: {
    type: Object,
    required: true,
    default: {
      firstName: "NOT AVAILABLE",
      lastName: "NOT AVAILABLE",
      balance: 0.00
    }
  },
  products: {
    type: Array,
    required: true
  }
})

const emit = defineEmits(['refreshCustomer'])

const isActive = ref(false);

</script>

<template>
  <v-dialog v-model="isActive">
    <template v-slot:activator="{props: activatorProps}">
      <v-card elevation="2" v-bind="activatorProps" variant="tonal" height="75" class="align-content-center v-card--hover" style="user-select: none">
        <v-row no-gutters class="d-flex align-center justify-center">
          <v-col
            class="d-flex  justify-center"
            cols="12"
            sm="3">
            <v-avatar
              v-if="!customer.community"
              class="elevation-1"
              icon="mdi-account"
            >
            </v-avatar>
            <v-avatar
              v-else
              class="elevation-1"
              icon="mdi-account-multiple"
            >
            </v-avatar>
          </v-col>
          <v-col
            class="d-flex align-center"
            cols="12"
            sm="9">
            <v-card-title><span class="ml-3">{{ props.customer.lastName }}, {{ props.customer.firstName }}</span></v-card-title>
            <v-spacer/>
            <v-icon v-if="customer.locked" color="primary" class="pr-5" icon="mdi-lock"></v-icon>
          </v-col>
        </v-row>
      </v-card>
    </template>
    <template v-slot:default>
      <CustomerAdminCard :customer :products @cancel-edit="isActive = false" @submit-edit="emit('refreshCustomer')"/>
    </template>
  </v-dialog>
</template>

<style scoped>

</style>
