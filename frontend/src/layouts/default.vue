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
</script>
