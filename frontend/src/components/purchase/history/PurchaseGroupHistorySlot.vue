<script setup>
import {onMounted, ref} from "vue";
import PurchaseHistoryCard from "@/components/purchase/history/PurchaseHistoryCard.vue";
import {useNotifyStore, useNotifyValidationStore} from "@/stores/app.js";
import {revertPurchase} from "@/api/purchaseApi.js";
import PurchaseHistoryCardRefactor from "@/components/purchase/history/refactor/PurchaseHistoryCardRefactor.vue";

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
      <v-card :color="!purchaseGroup.items[0].reversed ? 'default' : 'primary'" elevation="2" v-bind="activatorProps" variant="tonal" class="align-content-center v-card--hover" style="user-select: none">
        <v-row>
          <v-col
          cols="12"
          sm="10">
            <v-card-title class="d-flex align-center pb-0">
              <v-avatar
                v-if="!purchaseGroup.items[0].reversed"
                class="elevation-1"
                icon="mdi-package-variant"
              />
              <v-avatar
                v-else
                class="elevation-1"
                icon="mdi-package-variant-remove"
              />
              <div class="d-inline">
                <v-card-title v-if="!purchaseGroup.items[0].reversed">
                  Bestellung #{{ purchaseGroup.id }}
                </v-card-title>
                <v-card-title v-else>
                  Gelöschte Bestellung #{{ purchaseGroup.items[0].reversedGroupReference }}
                </v-card-title>
              </div>
            </v-card-title>
            <v-divider class="ml-2 my-1"></v-divider>
            <v-card-subtitle>
              {{ toReadableDate(purchaseGroup.items[0].creationTime) }} Uhr
            </v-card-subtitle>
            <v-card-text>
              <div class="d-flex opacity-90">
                <span class="pr-1">Bestellung:</span>
                <div v-for="(product, index) in products" :key="product.id"> <span>{{ product.name }}</span><span class="pr-1" v-if="index <= products.length-2">,</span> <span class="pl-1" v-else-if="index === 6">...</span> </div>
              </div>
            </v-card-text>
          </v-col>
          <v-col
            class="justify-end d-flex"
            cols="12"
            sm="2">
            <v-dialog v-model="isActiveInner" attach="body" @click.stop>
              <template v-slot:activator="{props: activatorProps}" >
                <v-card-actions
                  v-if="validateAllGroupPurchasesFullyReversed()"
                  v-bind="activatorProps">
                  <v-divider vertical class="mr-3"></v-divider>
                  <v-sheet class="border-lg rounded mr-3 elevation-3" color="secondary">
                    <v-icon size="45" icon="mdi-arrow-u-left-top"></v-icon>
                  </v-sheet>
                </v-card-actions>
                <v-card-actions
                  v-else
                  @click.stop="emit('jumpToReferencePurchaseGroup', purchaseGroup)">
                  <v-divider vertical class="mr-3"></v-divider>
                  <v-sheet class="border-lg rounded mr-3 elevation-3" color="secondary">
                    <v-icon size="45" icon="mdi-link-variant"></v-icon>
                  </v-sheet>
                </v-card-actions>
              </template>
              <template v-slot:default>
                <v-container
                  class="d-flex justify-center align-center">
                  <v-card
                    width="400"
                    title="Gesamte Bestellung löschen?"
                    subtitle="Dieser Vorgang kann nicht rückgängig gemacht werden.">
                    <v-card-actions class="justify-space-evenly">

                      <div class="pr-2 d-flex align-center justify-space-evenly flex-grow-1">
                        <v-btn border class="mr-3" color="primary" icon="mdi-close" @click="isActiveInner = !isActiveInner"></v-btn>
                        <v-divider vertical thickness="1" opacity="0.15"></v-divider>
                        <v-btn border class="ml-3" color="secondary" icon="mdi-arrow-right" @click="submitRemainingPurchases()"></v-btn>
                      </div>
                    </v-card-actions>
                  </v-card>

                </v-container>
              </template>
            </v-dialog>
          </v-col>
        </v-row>
      </v-card>
    </template>
    <template v-slot:default>
      <PurchaseHistoryCardRefactor :purchaseGroup @cancel-purchase-edit="isActive = !isActive" @submit-purchase-edit="submitPurchaseEdit"/>
    </template>
  </v-dialog>
</template>

<style scoped>

</style>
