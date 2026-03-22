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

const submitPurchaseEdit = async (purchases) => {
  if (!purchases.size > 0) {
    valNotify.set("Du musst mindestens eine Bestellung zurücksetzen!")
    return
  }
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

</script>

<template>
  <v-dialog v-model="isActive">
    <template v-slot:activator="{props: activatorProps}">
      <v-card :color="!purchaseGroup.items[0].reversed ? 'default' : 'red'" elevation="2" v-bind="activatorProps" variant="tonal" class="align-content-center v-card--hover" style="user-select: none">
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
            <v-card-actions
              @click.stop="emit('jumpToReferencePurchaseGroup', purchaseGroup)">
              <v-divider vertical class="mr-3"></v-divider>
              <v-sheet class="border-lg rounded mr-3 elevation-3" color="green">
                <v-icon v-if="validateAllGroupPurchasesFullyReversed()" size="45" icon="mdi-arrow-u-left-top"></v-icon>
                <v-icon v-else size="45" icon="mdi-link-variant"></v-icon>
              </v-sheet>
            </v-card-actions>
          </v-col>
        </v-row>
      </v-card>
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
