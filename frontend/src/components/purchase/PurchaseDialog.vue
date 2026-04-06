<script setup>
import {ref, watch} from "vue";
import { useNotifyValidationStore} from "@/stores/app.js";
import PurchaseCard from "@/components/purchase/PurchaseCard.vue";
import CustomerSlot from "@/components/customer/CustomerSlot.vue";

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

const emit = defineEmits(['refreshCustomer'])

const isActive = ref(false);

const refreshCustomerAndExit = async () => {
  emit('refreshCustomer', props.customer)
  isActive.value = false
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
      <CustomerSlot v-bind="activatorProps" :customer />
    </template>
    <template v-slot:default>
      <v-card height="700" >
        <v-card-item >
          <PurchaseCard :customer :products @submit="refreshCustomerAndExit" @close="isActive = false" />
        </v-card-item>
      </v-card>
    </template>
  </v-dialog>
</template>

<style scoped>

</style>
