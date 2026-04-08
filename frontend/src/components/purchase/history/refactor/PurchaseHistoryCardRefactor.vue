<script setup>

import PurchaseHistoryGrid from "@/components/purchase/history/PurchaseHistoryGrid.vue";
import {onMounted, ref} from "vue";
import {getCustomerById} from "@/api/customerApi.js";
import BasicDialogCard from "@/components/common/card/BasicDialogCard.vue";
import CloseSubmitBtn from "@/components/common/card/actions/CloseSubmitBtn.vue";

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
  }
})

const emit = defineEmits(['cancelPurchaseEdit', 'submitPurchaseEdit'])

const selectedPurchases = ref(new Map)
const customer = ref({})
const reversedPurchase = ref()

onMounted(async () => {
  customer.value = await getCustomerById(props.purchaseGroup.items[0].customerId)
  let containsReversedP;
  props.purchaseGroup.items.forEach(item => {
    if (item.reversedGroupReference) containsReversedP = item.reversedGroupReference
  })
  reversedPurchase.value = containsReversedP;
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

const handlePurchaseSelect = (purchase) => {
  if (selectedPurchases.value.get(purchase.id)){
    selectedPurchases.value.delete(purchase.id)
  } else {
    selectedPurchases.value.set(purchase.id, purchase)
  }
}
</script>

<template>
  <BasicDialogCard height="80vh">
    <template #header>
      <v-card-title class="pb-3">
        <v-row no-gutters>
          <v-col
            class="d-flex justify-space-between"
            cols="12"
            sm="4">
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
          </v-col>
          <v-col
            class=""
            cols="12"
            sm="4">
            <div class="text-h6">Benutzer</div>
            <v-card-subtitle class="pa-0">
              <h3>{{customer.lastName}}<span v-if="!!customer.firstName">, {{customer.firstName}}</span></h3>
            </v-card-subtitle>
          </v-col>
        </v-row>
      </v-card-title>
    </template>
    <template #header-actions>
      <CloseSubmitBtn class="justify-end" @close=" emit('cancelPurchaseEdit')" @submit="emit('submitPurchaseEdit', selectedPurchases)"></CloseSubmitBtn>
    </template>
    <template #body>
      <PurchaseHistoryGrid :purchases="purchaseGroup.items" @purchase-select="handlePurchaseSelect"/>
    </template>
  </BasicDialogCard>
</template>

<style scoped>

</style>
