<script setup>
import PurchaseCard from "@/components/PurchaseCard.vue";
import {tr} from "vuetify/locale";

const props = defineProps({
  customer: {
    type: Object,
    required: true
  }
})


</script>

<template>
  <v-dialog>
    <template v-slot:activator="{props: activatorProps}">
      <v-card elevation="2" v-bind="activatorProps" variant="tonal" height="75" class="align-content-center v-card--hover">
        <span class="ml-3">{{ props.customer.lastName }}, {{ props.customer.firstName }}</span> </v-card>
    </template>

    <template v-slot:default="{isActive}">
      <v-card>
        <PurchaseCard></PurchaseCard>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn
            text="Close Dialog"
            @click="isActive.value = false"
          ></v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<style scoped>

</style>
