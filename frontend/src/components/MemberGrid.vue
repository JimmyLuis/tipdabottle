<script setup>
import MemberSlot from "@/components/MemberSlot.vue";
import {onMounted, ref} from "vue";
import {apiFetch} from "@/api/http.js";

const apiUrl = import.meta.env.VITE_API_URL

const customers = ref([]);
const customersDummy = ref([]);


onMounted(async () => {
  for (let i = 0; i < 10; i++) {
    let customer = {
      id: i
    }
    customersDummy.value.push(customer);
  }
  customers.value = await apiFetch("/customers")
})





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
          <MemberSlot v-if="customers" :customer />
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
