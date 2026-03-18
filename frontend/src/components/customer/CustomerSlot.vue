<script setup>
import PurchaseCard from "@/components/purchase/PurchaseCard.vue";
import {tr} from "vuetify/locale";

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


</script>

<template>
  <v-dialog>
    <template v-slot:activator="{props: activatorProps}">
      <v-card elevation="2" v-bind="activatorProps" variant="tonal" height="75" class="align-content-center v-card--hover" style="user-select: none">
        <v-row no-gutters class="d-flex align-center justify-center">
          <v-col
            class="d-flex  justify-center"
          cols="12"
          sm="3">
            <v-avatar
              class="elevation-1"
            icon="mdi-account"
            >
            </v-avatar>
          </v-col>
          <v-col
            cols="12"
            sm="9">
            <span class="ml-3">{{ props.customer.lastName }}, {{ props.customer.firstName }}</span>
          </v-col>
        </v-row>
      </v-card>
    </template>
    <template v-slot:default="{isActive}">
      <v-card height="700" >
        <v-card-item >
          <PurchaseCard :customer :products ></PurchaseCard>
        </v-card-item>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            text="Schließen (ändern)"
            @click="isActive.value = false"
          ></v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<style scoped>

</style>
