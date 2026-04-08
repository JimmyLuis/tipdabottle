<script setup>
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
        reversedReference: 0,
        reversedGroupReference: 0
      }]
    }
  },
  reversedPurchase: {
    type: Number
  }
})


function calculatePurchaseGroupWorth() {
  let worth = 0.0
  props.purchaseGroup.items.forEach(purchase => {
    worth = worth + purchase.worth
  })
  props.purchaseGroup.items.forEach(purchase => {
    if (!!purchase.reversedReference){
      worth = worth - purchase.worth
    }
  })
  //hinzugefügt weil -0.00 anzeige war
  if (worth < 0.0099) worth *= -1
  return worth
}
</script>

<template>
  <div class="pr-3">
    <div class="d-flex align-end">
      Bestellung #{{purchaseGroup.id}}
      <h6 class="pb-1 pl-3 opacity-70" v-if="!!reversedPurchase">Bestellreferenz #{{reversedPurchase}}</h6>
      <v-spacer></v-spacer>
    </div>
    <v-card-subtitle class="pa-0">
      <div class="d-flex justify-start align-center pb-1">
        <div class="d-flex">
          <h3 class="pr-3"> Bestellwert: </h3>
          <h3 class="justify-end d-flex">{{calculatePurchaseGroupWorth().toFixed(2)}} €</h3>
        </div>
        <v-spacer></v-spacer>
      </div>
    </v-card-subtitle>
  </div>
</template>

<style scoped>

</style>
