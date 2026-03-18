<script setup>
import ProductGrid from "@/components/product/ProductGrid.vue";

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

</script>

<template>
  <v-card>
    <div class="pl-4">
      <v-card-title>
        <v-row no-gutters>
          <v-col
          cols="12"
          sm="8">
            {{props.customer?.lastName}}<span v-if="!!props.customer.lastName">,</span>
            {{props.customer?.firstName}}
            <v-card-subtitle>
              <h3> Saldo: {{props.customer.balance.toFixed(2)}} €</h3>
            </v-card-subtitle>
          </v-col>
          <v-col
            cols="12"
          sm="4">
            <v-row no-gutters>
              <v-col class="d-flex align-center justify-center">

                <v-btn icon="mdi-close"></v-btn>
              </v-col>
              <v-col class="d-flex align-center justify-center">

                <v-btn icon="mdi-check"></v-btn>
              </v-col>
            </v-row>
          </v-col>
        </v-row>

      </v-card-title>
    </div>
    <v-card-text>
      <v-banner class="pt-0"></v-banner>
    </v-card-text>
    <v-card-item class="pt-0 px-0" >
      <ProductGrid :products ></ProductGrid>
    </v-card-item>
  </v-card>
</template>

<style scoped>

</style>
