<script setup>
import CustomerAdminEditForm from "@/components/customer/admin/CustomerAdminEditForm.vue";
import BasicDialogCard from "@/components/common/card/BasicDialogCard.vue";
import CloseBtn from "@/components/common/card/actions/CloseBtn.vue";

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

const emit = defineEmits(['cancelEdit', 'submitEdit'])

</script>

<template>

  <BasicDialogCard>
    <template #header>
      <div class="pr-3">
        {{props.customer?.lastName}}<span v-if="!!props.customer.lastName">,</span>
        {{props.customer?.firstName}}
        <v-card-subtitle class="pa-0">
          <div class="d-flex justify-start align-center pb-1">
            <div class="d-flex">
              <h3 class="pr-3"> Saldo: </h3>
              <div class="d-inline">
                <h3 class="justify-end d-flex">{{props.customer.balance.toFixed(2)}} €</h3>
              </div>
            </div>
            <v-spacer></v-spacer>
          </div>
        </v-card-subtitle>
      </div>
    </template>
    <template #header-actions >
      <div class="d-flex justify-end">
        <CloseBtn @close="emit('cancelEdit')"/>
      </div>
    </template>
    <template #body>
      <CustomerAdminEditForm :customer @submit-edit="emit('submitEdit')" />
    </template>
  </BasicDialogCard>

</template>

<style scoped>
</style>
