<script setup>
import CustomerSlot from "@/components/customer/CustomerSlot.vue";
import {onMounted, ref} from "vue";
import {apiFetch} from "@/api/http.js";
import {getAllCustomers, getCustomerById} from "@/api/customerApi.js";
import {getAllProducts} from "@/api/productApi.js";
import {useNotifyValidationStore} from "@/stores/app.js";
import PurchaseGroupHistorySlot from "@/components/purchase/history/PurchaseGroupHistorySlot.vue";
import {getAllPurchases} from "@/api/purchaseApi.js";


const purchaseGroupsDummy = ref([]);
const purchaseGroups = ref([]);
const page = ref(0)
const loading = ref(false)
const finished = ref(false)
const observerTarget = ref(null)


onMounted(async () => {
  for (let i = 0; i < 10; i++) {
    let purchaseGroup = {
      id: i
    }
    purchaseGroupsDummy.value.push(purchaseGroup);
  }
  await loadMore()
  const observer = new IntersectionObserver(async (entries) => {
    if (entries[0].isIntersecting) {
      await loadMore()
    }
  })
  if (observerTarget.value) {
    observer.observe(observerTarget.value)
  }
})

const loadMore = async () => {
  if (loading.value || finished.value) return

  loading.value = true
  const pageResponse = await getAllPurchases(page.value, 15)

  purchaseGroups.value.push(...pageResponse.content)

  // 👉 nächste Seite
  page.value++

  // 👉 fertig wenn letzte Seite
  if (pageResponse.page.number >= pageResponse.page.totalPages - 1) {
    finished.value = true
  }

  loading.value = false
}

const refresh = () => {
  finished.value = false
  page.value = 0
  purchaseGroups.value = []
  loadMore()
}

</script>

<template>
  <v-container fluid class="px-5 fill-height flex-grow-1 d-inline">
    <v-row @scroll="loadMore"  no-gutters v-if="purchaseGroups.length > 0">
      <v-col
        class=" overflow-y-auto"
        v-for="purchaseGroup in purchaseGroups"
        :key="purchaseGroup.id"
        cols="12"
        sm="12"
      >
        <v-sheet class="ma-2 pa-2" >
          <PurchaseGroupHistorySlot :purchaseGroup @refresh-purchases="refresh"/>
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
    <div class="d-flex justify-center flex-grow-1">
      <div ref="observerTarget" style="height: 20px;"></div>

      <v-progress-circular
        v-if="loading"
        indeterminate
        class="ma-4"
      />
    </div>
  </v-container>
</template>

<style scoped>

</style>
