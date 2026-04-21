<script setup>
import {onMounted, ref} from "vue";
import {getAllCustomersWithInactive, getCustomerById} from "@/api/customerApi.js";
import {getAllProducts} from "@/api/productApi.js";
import CustomerAdminAddSlot from "@/components/customer/admin/add/CustomerAdminAddSlot.vue";
import CustomerAdminDialog from "@/components/customer/admin/CustomerAdminDialog.vue";


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
  await refreshAllCustomers()
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

const refreshAllCustomers = async () => {
  customers.value = await getAllCustomersWithInactive();
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
      <v-col cols="12" sm="12">
        <CustomerAdminAddSlot @refresh-all-customers="refreshAllCustomers"/>
      </v-col>
      <v-col
        v-for="customer in customers"
        :key="customer.id"
        cols="12"
        sm="4"
      >
        <v-sheet class="ma-2 pa-2">
          <CustomerAdminDialog v-if="customers" :customer @refresh-customer="refreshAllCustomers"/>
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
