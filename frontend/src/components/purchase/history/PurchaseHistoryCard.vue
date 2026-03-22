<script setup>


import PurchaseHistoryGrid from "@/components/purchase/history/PurchaseHistoryGrid.vue";
import {ref} from "vue";

const props = defineProps({
  purchaseGroup: {
    type: Object,
    required: true,
    default: {
      id: 0,
      items: [{
        id: 1,
        purchaseGroupId: 0,
        product: null,
        customerId: 0,
        creationTime: Date.now(),
        worth: 0.00,
        reversed: false,
        quantity: 0,
        reversedReference: 0
      }]
    }
  }
})

const emit = defineEmits(['cancelPurchaseEdit', 'submitPurchaseEdit'])

const selectedPurchases = ref(new Map)

function calculatePurchaseGroupWorth() {
  let worth = 0.0
  props.purchaseGroup.items.forEach(purchase => {
    worth = worth + purchase.worth
  })
  return worth
}

const handlePurchaseSelect = (purchase) => {
  if (selectedPurchases.value.get(purchase.id)){
    selectedPurchases.value.delete(purchase.id)
  } else {
    selectedPurchases.value.set(purchase.id, purchase)
  }
}
</script>

<template>
  <v-card>
    <div class="pl-4">
      <v-card-title class="pb-3">
        <v-row no-gutters>
          <v-col
            class="d-flex justify-space-between"
            cols="12"
            sm="8">
            <div class="pr-3">
              <div class="d-flex">
                Bestellung #{{purchaseGroup.id}}
                <v-spacer></v-spacer>
                <div class="pl-5 d-none d-sm-flex text-body-2 opacity-70  align-center">{{purchaseGroup.items[0].creationTime}}</div>
              </div>
              <v-card-subtitle>
                <div class="d-flex justify-start align-center pb-1">
                  <div class="d-flex">
                    <h3 class="pr-3"> Bestellwert: </h3>
                    <h3 class="justify-end d-flex">{{calculatePurchaseGroupWorth().toFixed(2)}} €</h3>
                  </div>
                  <v-spacer></v-spacer>
                </div>
              </v-card-subtitle>
            </div>
          </v-col>
          <v-col
            class="d-flex justify-md-end justify-sm-center"
            cols="12"
            sm="4">
            <div class="pr-2 d-flex align-center pb-8">
              <v-btn class="mr-3" color="red" icon="mdi-close" @click="emit('cancelPurchaseEdit')"></v-btn>
              <v-divider vertical thickness="1" opacity="0.15"></v-divider>
              <v-btn class="ml-3" color="green" icon="mdi-arrow-right" @click="emit('submitPurchaseEdit', selectedPurchases)"></v-btn>
            </div>
          </v-col>
        </v-row>
      </v-card-title>
    </div>
    <v-card-text>
      <v-banner class="pt-0"></v-banner>
    </v-card-text>
    <v-card-item class="pt-0 px-0" >
      <PurchaseHistoryGrid :purchases="purchaseGroup.items" @purchase-select="handlePurchaseSelect"/>
    </v-card-item>
  </v-card>
</template>

<style scoped>

</style>
