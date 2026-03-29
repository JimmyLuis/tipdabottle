<script setup>
import CustomerSlot from "@/components/customer/CustomerSlot.vue";
import {onMounted, ref} from "vue";
import {apiFetch} from "@/api/http.js";
import {getAllCustomers, getAllCustomersWithInactive, getCustomerById} from "@/api/customerApi.js";
import {getAllProducts} from "@/api/productApi.js";
import {useNotifyValidationStore} from "@/stores/app.js";
import CustomerAdminSlot from "@/components/customer/admin/CustomerAdminSlot.vue";


const customers = ref([]);
const customersDummy = ref([]);

const products = ref([])



onMounted(async () => {
  for (let i = 0; i < 10; i++) {
    let customer = {
      id: i
    }
    customersDummy.value.push(customer);
  }
  customers.value = await getAllCustomersWithInactive();
  customers.value.sort((a, b) => {
    if (b.community !== a.community) {
      return b.community - a.community
    }
    return a.lastName.localeCompare(b.lastName)
  })
  products.value = await getAllProducts();
})


const refreshCustomer = async (customer) => {
  customers.value = customers.value.filter(item => item.id !== customer.id);
  customers.value.push(await getCustomerById(customer.id))
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
          <CustomerAdminSlot v-if="customers" :customer :products @refresh-customer="refreshCustomer"/>
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
