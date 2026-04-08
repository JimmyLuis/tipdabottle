<script setup>
import {onMounted, ref} from "vue";
import PurchaseHistoryCard from "@/components/purchase/history/PurchaseHistoryCard.vue";
import {useNotifyStore, useNotifyValidationStore} from "@/stores/app.js";
import {revertPurchase} from "@/api/purchaseApi.js";

const props = defineProps({
  purchaseGroup: {
    type: Object,
    required: true,
    default: {
      creationTime: Date.now(),
    }
  }
})

const emit = defineEmits(['refreshPurchases', 'jumpToReferencePurchaseGroup'])

const products = ref([])
const valNotify = useNotifyValidationStore()
const notify = useNotifyStore()

onMounted(() => {
  products.value = props.purchaseGroup.items.map(item => item.product).slice(0,7)
})

function toReadableDate(date){
  return new Date(date).toLocaleString('de-DE', {
    weekday: 'long',
    day: '2-digit',
    month: 'long',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const isActive = ref(false);
const isActiveInner = ref(false);

const submitPurchaseEdit = async (purchases) => {
  if (!purchases.size > 0) {
    valNotify.set("Du musst mindestens eine Bestellung zurücksetzen!")
    return
  }

  let now = Date.now();
  let tooLongAgo = false;
  purchases.forEach((purchase ,id) => {
    let timestamp = new Date(purchase.creationTime).getTime();
    let isOlderThan15Min = timestamp < (now - 15 * 60 * 1000);
    if (isOlderThan15Min) {
      valNotify.set('Die Bestellung liegt zu lange zurück und kann nicht zurückgesetzt werden!')
      tooLongAgo = true;
    }
  })
  if (tooLongAgo) return
  isActive.value = !isActive.value
  const res = await revertPurchase(purchases)
  notify.set("Deine Bestellung wurde angepasst!")
  emit('refreshPurchases')
}

function validateAllGroupPurchasesFullyReversed(){
  let allReversed = false;
  props.purchaseGroup.items.forEach(item => {
    if (!!item.reversedGroupReference) {
      return false
    } else {
      allReversed = true;
    }
  })
  return allReversed
}
async function submitRemainingPurchases() {
  isActiveInner.value = !isActiveInner.value
  let purchases = props.purchaseGroup.items.filter(purchase => !purchase.reversedGroupReference)
  let mappedPurchase = new Map
  purchases.forEach(purchase => {
    mappedPurchase.set(purchase.id, purchase)
  })
  await submitPurchaseEdit(mappedPurchase)
}

</script>

<template>
  <v-dialog v-model="isActive">
    <template v-slot:activator="{props: activatorProps}">
      <PurchaseGroupHistorySlotRefactor :purchaseGroup @refresh-purchases="refresh" @jump-to-reference-purchase-group="scrollToPurchaseGroup"/>
    </template>
    <template v-slot:default>
      <v-card height="700" >
        <v-card-item >
          <PurchaseHistoryCard :purchaseGroup @cancel-purchase-edit="isActive = !isActive" @submit-purchase-edit="submitPurchaseEdit"/>
        </v-card-item>
      </v-card>
    </template>
  </v-dialog>
</template>

<style scoped>

</style>
