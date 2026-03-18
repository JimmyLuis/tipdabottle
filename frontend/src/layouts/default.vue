<template>
  <v-app>
    <NavigationBar/>
    <v-main>
      <router-view/>
    </v-main>
    <FooterBar/>
  </v-app>
</template>

<script setup>
  //
import NavigationBar from "@/components/navigation/NavigationBar.vue";
  import FooterBar from "@/components/navigation/FooterBar.vue";
</script>
