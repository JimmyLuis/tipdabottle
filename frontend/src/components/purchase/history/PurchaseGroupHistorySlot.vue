<script setup>
import {onMounted, ref} from "vue";
import PurchaseHistoryCard from "@/components/purchase/history/PurchaseHistoryCard.vue";
import {useNotifyValidationStore} from "@/stores/app.js";

const props = defineProps({
  purchaseGroup: {
    type: Object,
    required: true,
    default: {
      creationTime: Date.now(),
    }
  }
})

const products = ref([])
const valNotify = useNotifyValidationStore()

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

const submitPurchaseEdit = (purchases) => {
  console.log(purchases)
  if (purchases.lenght) valNotify.set("Du musst mindestens eine Bestellung zurücksetzen!")
}

</script>

<template>
  <v-dialog v-model="isActive">
    <template v-slot:activator="{props: activatorProps}">
      <v-card elevation="2" v-bind="activatorProps" variant="tonal" class="align-content-center v-card--hover" style="user-select: none">
        <v-row>
          <v-col
          cols="12"
          sm="10">
            <v-card-title class="d-flex align-center pb-0">
              <v-avatar
                class="elevation-1"
                icon="mdi-package-variant"
              />
              <div class="d-inline">
                <v-card-title>
                  Bestellung #{{ purchaseGroup.id }}
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
            <v-card-actions>
              <v-divider vertical class="mr-3"></v-divider>
              <v-sheet class="border-lg rounded-sm mr-3 elevation-3" color="green">
                <v-icon size="45" icon="mdi-arrow-u-left-top"></v-icon>
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
