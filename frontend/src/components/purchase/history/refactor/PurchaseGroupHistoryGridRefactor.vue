<script setup>
import {onMounted, ref} from "vue";
import {useNotifyStore, useNotifyValidationStore} from "@/stores/app.js";
import PurchaseGroupHistorySlot from "@/components/purchase/history/PurchaseGroupHistorySlot.vue";
import {getAllPurchases} from "@/api/purchaseApi.js";


const purchaseGroupsDummy = ref([]);
const purchaseGroups = ref([]);
const purchaseGroupJumpRefs = ref([])
const activeJumpElement = ref(null)
const page = ref(0)
const loading = ref(false)
const finished = ref(false)
const observerTarget = ref(null)
const notifyVal = useNotifyValidationStore()
const notify = useNotifyStore()


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

  page.value++

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

const scrollToPurchaseGroup = async (purchaseGroup) => {
  notify.set('Suche...')
  for (let i = 0; i < 50; i++) {
    if (!!purchaseGroupJumpRefs.value[purchaseGroup.id]) {
      await loadMore()
    } else {
      break
    }
    if (i === 50) {
      notifyVal.set('Die verbundene Bestellung konnte nicht gefunden werden. Sie liegt zu lange zurück!')
      return
    }
  }
  purchaseGroupJumpRefs.value[purchaseGroup.items[0].reversedGroupReference].$el.scrollIntoView({
    behavior: 'smooth',
    block: 'center'
  })
  activeJumpElement.value = purchaseGroup.items[0].reversedGroupReference

  // nach kurzer Zeit wieder entfernen (optional)
  setTimeout(() => {
    activeJumpElement.value = null
  }, 2000)
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
        <v-sheet class="ma-2 pa-2 group-item" :class="{ active: activeJumpElement === purchaseGroup.id }" :ref="el => purchaseGroupJumpRefs[purchaseGroup.id] = el">
          <PurchaseHistoryDialog :purchaseGroup @refresh-purchases="refresh" @jump-to-reference-purchase-group="scrollToPurchaseGroup"/>
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
.group-item {
  transition: all 0.3s ease;
}

@keyframes pop {
  0% {
    transform: scale(1);
    background-color: transparent;
  }
  50% {
    transform: scale(1.05);
    background-color: rgba(76, 175, 80, 0.15); /* 🔥 leicht grün */
  }
  100% {
    transform: scale(1);
    background-color: transparent;
  }
}

.group-item.active {
  animation: pop 2s ease;
}
</style>
