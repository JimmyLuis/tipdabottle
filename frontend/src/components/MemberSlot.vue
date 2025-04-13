<script setup>

import PurchaseCard from "@/components/PurchaseCard.vue";
</script>

<template>
  <v-dialog>
    <template v-slot:activator="{props: activatorProps}">
      <v-card v-bind="activatorProps" variant="tonal" height="75" class="align-content-center v-card--hover"> <span class="ml-3">Name, Vorname</span> </v-card>
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
