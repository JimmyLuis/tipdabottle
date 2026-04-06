<script setup>

import {onBeforeMount, onMounted, ref, watch} from "vue";
import {getCustomerById} from "@/api/customerApi.js";
import PurchaseDialog from "@/components/purchase/PurchaseDialog.vue";
import {useCustomerStore, useProductStore} from "@/stores/app.js";


const customers = ref([]);
const customersDummy = ref([]);

const products = ref([])

onBeforeMount(async () => {
  await useCustomerStore().fetchCustomers()
  await useProductStore().fetchProducts()
  customers.value = useCustomerStore().customers
  sortCustomers()
  products.value = useProductStore().products
})

onMounted(async () => {
  for (let i = 0; i < 20; i++) {
    let customer = {id: i}
    customersDummy.value.push(customer);
  }
})

const sortCustomers = () => {
  customers.value.sort((a, b) => {
    if (b.community !== a.community) {
      return b.community - a.community
    }
    return a.lastName.localeCompare(b.lastName)
  })
}


const refreshCustomer = async (customer) => {
  const updated = await getCustomerById(customer.id)
  const index = customers.value.findIndex(c => c.id === customer.id)
  if (index !== -1) {
    customers.value[index] = updated
  }
  customers.value.sort((a, b) => {
    if (b.community !== a.community) {
      return b.community - a.community
    }
    return a.lastName.localeCompare(b.lastName)
  })
}



</script>

<template>
  <v-container fluid class="px-5">
    <v-row no-gutters v-if="customers.length > 0">
      <v-col
        v-for="customer in customers"
        :key="customer.id"
        cols="12"
        sm="4"
      >
        <v-sheet class="ma-2 pa-2">
          <PurchaseDialog :products :customer @refresh-customer="refreshCustomer"/>
        </v-sheet>
      </v-col>
    </v-row>
    <v-row v-else no-gutters>
      <v-col
        v-for="customer in customersDummy"
        :key="customer.id"
        cols="12"
        sm="4"
      >
        <v-sheet class="ma-2 pa-2">
          <v-skeleton-loader elevation="2" type="paragraph"></v-skeleton-loader>
        </v-sheet>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>

</style>
