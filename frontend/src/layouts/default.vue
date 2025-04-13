<template>
  <v-main>
    <NavigationBar />
    <member-grid class="mt-5"></member-grid>
    <purchase-card></purchase-card>
  </v-main>
</template>

<script setup>
  //
import NavigationBar from "@/components/NavigationBar.vue";
  import MemberGrid from "@/components/MemberGrid.vue";
  import PurchaseCard from "@/components/PurchaseCard.vue";
</script>
