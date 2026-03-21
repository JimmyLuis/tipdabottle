<script setup>
import CustomerSlot from "@/components/customer/CustomerSlot.vue";
import {onMounted, ref} from "vue";
import {apiFetch} from "@/api/http.js";
import {getAllCustomers, getCustomerById} from "@/api/customerApi.js";
import {getAllProducts} from "@/api/productApi.js";
import {useNotifyValidationStore} from "@/stores/app.js";
import PurchaseGroupHistorySlot from "@/components/purchase/history/PurchaseGroupHistorySlot.vue";
import {getAllPurchases} from "@/api/purchaseApi.js";


const purchaseGroups = ref([]);
const purchaseGroupsDummy = ref([]);
const page = ref(0)
const loading = ref(false)
const finished = ref(false)



onMounted(async () => {
  for (let i = 0; i < 10; i++) {
    let purchaseGroup = {
      id: i
    }
    purchaseGroupsDummy.value.push(purchaseGroup);
  }
  loading.value = true
  let pageResponse = await getAllPurchases(page.value, 15);
  console.log(pageResponse)
  purchaseGroups.value.push(...pageResponse.content)
  page.value = pageResponse.page.number
  if (page.value === pageResponse.page.number) {
    finished.value = true
  }
  loading.value = false
  console.log(purchaseGroups.value)
})

const loadMore = async () => {
  if (loading.value || finished.value) return
  loading.value = true
  let pageResponse = await getAllProducts(page, 15);
  purchaseGroups.value.push(pageResponse.content)
  page.value = pageResponse.page.number
  if (page.value === pageResponse.page.number) {
    finished.value = true
  }
  loading.value = false
}

</script>

<template>
  <v-container fluid class="px-5">
    <v-row @scroll.passive="loadMore" class="scroll-container" no-gutters v-if="purchaseGroups.length > 0">
      <v-col
        v-for="purchaseGroup in purchaseGroups"
        :key="purchaseGroup.id"
        cols="12"
        sm="12"
      >
        <v-sheet class="ma-2 pa-2">
          <PurchaseGroupHistorySlot :purchaseGroup />
        </v-sheet>
      </v-col>
    </v-row>
    <v-row v-else no-gutters>
      <v-col
        v-for="purchaseGroups in purchaseGroupsDummy"
        :key="purchaseGroups.id"
        cols="12"
        sm="12"
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
