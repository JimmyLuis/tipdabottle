<script setup>
import ProductGrid from "@/components/product/ProductGrid.vue";
import {ref} from "vue";
import {useNotifyValidationStore} from "@/stores/app.js";
import CustomerAdminEditForm from "@/components/customer/admin/CustomerAdminEditForm.vue";

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

const emit = defineEmits(['cancelEdit', 'submitEdit'])

</script>

<template>
  <v-card>
    <div class="pl-4">
      <v-card-title class="pb-3">
        <v-row no-gutters>
          <v-col
            class="d-flex justify-space-between"
            cols="12"
            sm="8">
            <div class="pr-3">
              {{props.customer?.lastName}}<span v-if="!!props.customer.lastName">,</span>
              {{props.customer?.firstName}}
              <v-card-subtitle class="pa-0">
                <div class="d-flex justify-start align-center pb-1">
                  <div class="d-flex">
                    <h3 class="pr-3"> Saldo: </h3>
                    <div class="d-inline">
                      <h3 class="justify-end d-flex">{{props.customer.balance.toFixed(2)}} €</h3>
                    </div>
                  </div>
                  <v-spacer></v-spacer>
                </div>
              </v-card-subtitle>
            </div>
          </v-col>
          <v-col
            class="d-flex justify-md-end justify-sm-center"
            cols="12"
            sm="4">
            <div class="pr-2 d-flex align-center pb-8">
              <v-btn color="primary" icon="mdi-close" @click="emit('cancelEdit')"></v-btn>
            </div>
          </v-col>
        </v-row>
      </v-card-title>
    </div>
    <v-card-text>
      <v-banner class="pt-0"></v-banner>
    </v-card-text>
    <v-card-item class="pt-0 px-0" >
      <CustomerAdminEditForm :customer></CustomerAdminEditForm>
    </v-card-item>
  </v-card>
</template>

<style scoped>
.scroll-container {
  position: relative;
  top: 20px;
  max-height: 75px;
  overflow-y: auto;
}

/* Fade unten */
.scroll-container::after {
  content: "";
  position: sticky;
  bottom: 0;
  display: block;
  height: 50px;

  background: linear-gradient(
    to bottom,
    rgba(255, 255, 255, 0),
    rgba(255, 255, 255, 1)
  );
}
</style>
