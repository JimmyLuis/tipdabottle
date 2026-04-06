<script setup>
const props = defineProps({
  customer: {
    type: Object,
    required: true,
    default: {
      firstName: "Max",
      lastName: "Mustermann",
      balance: 0.0,

    }
  }
})
</script>

<template>
  <div>
    <div>
      <div class="d-flex">
        {{props.customer?.lastName}}<div class="pr-2" v-if="!!props.customer.lastName">,</div>
        {{props.customer?.firstName}}
      </div>
      <v-card-subtitle v-if="props.customer?.balance === undefined" class="pa-0">
        <h3>
          <slot name="below-balance"/>
        </h3>
      </v-card-subtitle>
    </div>
    <v-card-subtitle class="pa-0 d-flex" v-if="props.customer?.balance !== undefined">
      <h3 > Saldo: </h3>
      <div>
        <h3 class="justify-end d-flex pl-3">{{props.customer.balance.toFixed(2)}} €</h3>
        <h3 class="justify-end d-flex">
          <slot name="below-balance"/>
        </h3>
      </div>
    </v-card-subtitle>
  </div>
</template>

<style scoped>

</style>
