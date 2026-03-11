<template>
  <v-app>
    <NavigationBar/>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script setup>
  //
import NavigationBar from "@/components/navigation/NavigationBar.vue";
  import MemberGrid from "@/components/MemberGrid.vue";
  import PurchaseCard from "@/components/PurchaseCard.vue";
</script>
