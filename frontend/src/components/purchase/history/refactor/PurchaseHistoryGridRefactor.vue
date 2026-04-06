<script setup>


import {onMounted, ref} from "vue";
import PurchaseHistorySlot from "@/components/purchase/history/PurchaseHistorySlot.vue";

const props = defineProps({
  purchases: {
    type: Array,
    required: true
  }
})
const emit = defineEmits(['purchaseSelect'])

const purchasesDummy = ref([])

onMounted(() =>{
  for (let i = 0; i < 10; i++) {
    purchasesDummy.value.push({
      id: i,
      purchaseGroupId: 1,
      product: {
        id: i,
        name: "Dummy",
        description: "Dummy Description",
        category: "dummy category",
        container: null,
        stock: 0,
      },
      customerId: 1,
      creationTime: Date.now(),
      worth: i*i + i,
      quantity: 1,
      reversed: false,
      reversedReference: null
    })
  }
})

const propagatePurchaseSelect = (purchase) => {
  emit('purchaseSelect', purchase)
}

</script>

<template>
  <v-container height="500" fluid class=" overflow-y-auto">
    <v-row v-if="purchases.length > 0">
      <v-col
        v-for="purchase in purchases"
        :key="purchase.id"
        cols="12"
        sm="12"
        class="d-inline"
      >
        <PurchaseHistorySlot :purchase @purchase-select="propagatePurchaseSelect"/>
      </v-col>
    </v-row>
    <v-row v-else no-gutters>
      <v-col
        v-for="purchase in purchasesDummy"
        :key="purchase.id"
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
