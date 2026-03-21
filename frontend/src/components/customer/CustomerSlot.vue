<script setup>
import PurchaseCard from "@/components/purchase/PurchaseCard.vue";
import {purchaseProducts} from "@/api/purchaseApi.js";
import {ref, watch} from "vue";
import {useNotifyStore, useNotifyValidationStore} from "@/stores/app.js";

const props = defineProps({
  customer: {
    type: Object,
    required: true,
    default: {
      firstName: "NOT AVAILABLE",
      lastName: "NOT AVAILABLE",
      balance: 0.00
    }
  },
  products: {
    type: Array,
    required: true
  }
})

const notify = useNotifyStore()

const emit = defineEmits(['refreshCustomer'])

const isActive = ref(false);


const submitPurchase = async function(submitProducts) {
  await purchaseProducts(props.customer.id, submitProducts.values().toArray())
    .then(res => {
      if (res)
        notify.set(`Vielen Dank für deine Bestellung! Dein Konto wurde angepasst ;)`)
    })
  isActive.value = false
  emit('refreshCustomer', props.customer)

}

const handleCustomerLocked = customer => {
  if (customer.locked) {
    useNotifyValidationStore().set("Hast du mal darüber nachgedacht einzuzahlen?!")
  }
}

watch(isActive, () => {
  if (isActive) handleCustomerLocked(props.customer)
})
</script>

<template>
  <v-dialog v-model="isActive">
    <template v-slot:activator="{props: activatorProps}">
      <v-card elevation="2" v-bind="activatorProps" variant="tonal" height="75" class="align-content-center v-card--hover" style="user-select: none">
        <v-row no-gutters class="d-flex align-center justify-center">
          <v-col
            class="d-flex  justify-center"
          cols="12"
          sm="3">
            <v-avatar
              v-if="!customer.community"
              class="elevation-1"
            icon="mdi-account"
            >
            </v-avatar>
            <v-avatar
              v-else
              class="elevation-1"
              icon="mdi-account-multiple"
            >
            </v-avatar>
          </v-col>
          <v-col
            class="d-flex"
            cols="12"
            sm="9">
            <span class="ml-3">{{ props.customer.lastName }}, {{ props.customer.firstName }}</span>
            <v-spacer/>
            <v-icon v-if="customer.locked" color="red" class="pr-5" icon="mdi-lock"></v-icon>
          </v-col>
        </v-row>
      </v-card>
    </template>
    <template v-slot:default>
      <v-card height="700" >
        <v-card-item >
          <PurchaseCard :customer :products @cancelPurchase="isActive = false" @submitPurchase="submitPurchase"></PurchaseCard>
        </v-card-item>
      </v-card>
    </template>
  </v-dialog>
</template>

<style scoped>

</style>
