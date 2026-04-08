<script setup>

import PurchaseHistoryGrid from "@/components/purchase/history/PurchaseHistoryGrid.vue";
import {onMounted, ref} from "vue";
import {getCustomerById} from "@/api/customerApi.js";
import BasicDialogCard from "@/components/common/card/BasicDialogCard.vue";
import CloseSubmitBtn from "@/components/common/card/actions/CloseSubmitBtn.vue";
import BasicCustomerInfo from "@/components/customer/BasicCustomerInfo.vue";
import BasicPurchaseInformation from "@/components/purchase/history/card/BasicPurchaseInformation.vue";

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
            sm="6">
            <BasicPurchaseInformation :purchase-group="purchaseGroup" :reversed-purchase="reversedPurchase"/>
          </v-col>
          <v-col
            class=""
            cols="12"
            sm="6">
            <BasicCustomerInfo :customer="customer" />
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
