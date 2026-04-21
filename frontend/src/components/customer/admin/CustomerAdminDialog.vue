<script setup>
import {ref, watch} from "vue";
import { useNotifyValidationStore} from "@/stores/app.js";
import CustomerSlot from "@/components/customer/CustomerSlot.vue";
import CustomerAdminCard from "@/components/customer/admin/CustomerAdminCard.vue";

const props = defineProps({
  customer: {
    type: Object,
    required: true,
    default: {
      firstName: "NOT AVAILABLE",
      lastName: "NOT AVAILABLE",
      balance: 0.00
    }
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
      <CustomerAdminCard :customer @submit-edit="refreshCustomerAndExit" @cancel-edit="isActive = false" />
    </template>
  </v-dialog>
</template>

<style scoped>

</style>
