<script setup>

</script>

<template>
  <v-app-bar :elevation="10" rounded color="red-darken-2" height="75">
    <template v-slot:prepend>
      <v-app-bar-nav-icon></v-app-bar-nav-icon>
    </template>

    <v-app-bar-title class="font-weight-bold">tipdabottle</v-app-bar-title>

    <template v-slot:append>
      <v-btn icon="mdi-account-cog-outline"></v-btn>
    </template>
  </v-app-bar>
</template>

<style scoped>

</style>
