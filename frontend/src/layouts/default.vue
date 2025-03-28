<template>
  <v-main>
    <NavigationBar />
    <member-slot></member-slot>
  </v-main>
</template>

<script setup>
  //
import NavigationBar from "@/components/NavigationBar.vue";
  import MemberSlot from "@/components/MemberSlot.vue";
</script>
