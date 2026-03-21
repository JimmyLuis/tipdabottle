<script setup>
import {ref} from "vue";
import PurchaseHistoryCard from "@/components/purchase/history/PurchaseHistoryCard.vue";

const props = defineProps({
  purchaseGroup: {
    type: Object,
    required: true,
    default: {
      creationTime: Date.now(),
    }
  }
})


const isActive = ref(false);

</script>

<template>
  <v-dialog v-model="isActive">
    <template v-slot:activator="{props: activatorProps}">
      <v-card elevation="2" v-bind="activatorProps" variant="tonal" height="75" class="align-content-center v-card--hover" style="user-select: none">
        <v-row no-gutters class="d-flex align-center justify-center">
          <v-col
            class="d-flex  justify-start pl-5"
            cols="12"
            sm="3">
            <v-avatar
              class="elevation-1"
              icon="mdi-account-multiple"
            >
            </v-avatar>
          </v-col>
          <v-col
            class="d-flex"
            cols="12"
            sm="9">
            <span class="ml-3">{{ props.purchaseGroup.creationTime }}</span>
          </v-col>
        </v-row>
      </v-card>
    </template>
    <template v-slot:default>
      <v-card height="700" >
        <v-card-item >
          <PurchaseHistoryCard/>
        </v-card-item>
      </v-card>
    </template>
  </v-dialog>
</template>

<style scoped>

</style>
