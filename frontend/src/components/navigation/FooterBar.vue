<script setup>
const links = [
  'Home',
  'About Us',
  'Team',
  'Services',
  'Blog',
  'Contact Us',
]
</script>

<template>
  <v-footer class="d-flex align-center justify-center ga-2 flex-wrap flex-grow-1 py-3 rounded-t-lg" color="red-darken-2">

    <div class="flex-1-0-100 text-center mt-2">
      {{ new Date().getFullYear() }} — <strong>tipdabottle</strong>
    </div>
  </v-footer>
</template>

<style scoped>

</style>
