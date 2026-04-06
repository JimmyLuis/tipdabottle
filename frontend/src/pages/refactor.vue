<script setup>
import PurchaseCard from "@/components/purchase/PurchaseCard.vue";
import {onBeforeMount, ref} from "vue";
import {useCustomerStore, useProductStore} from "@/stores/app.js";
import CustomerGrid from "@/components/customer/CustomerGrid.vue";
import PurchaseGroupHistoryGridRefactor
  from "@/components/purchase/history/refactor/PurchaseGroupHistoryGridRefactor.vue";

const customers = ref([])

onBeforeMount(async () => {
  if (!useCustomerStore().loaded) {
    await useCustomerStore().fetchCustomers()
    customers.value = useCustomerStore().customers
  }
  if (!useProductStore().loaded) {
    await useProductStore().fetchProducts()
  }
})

</script>

<template>
  <PurchaseGroupHistoryGridRefactor/>
</template>

<style scoped>

</style>
