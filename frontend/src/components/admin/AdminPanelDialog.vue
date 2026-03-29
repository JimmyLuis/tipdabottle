<script setup>

import {ref} from "vue";
import AdminLoginForm from "@/components/login/AdminLoginForm.vue";
import AdminPanelSelector from "@/components/admin/AdminPanelSelector.vue";

const isActive = ref(false)
const isLoggedIn = ref(false)

</script>

<template>

  <v-dialog v-model="isActive">
    <template v-slot:activator="{props: activatorProps}">
      <v-btn v-bind="activatorProps" icon="mdi-account-cog-outline"/>
    </template>
    <template v-slot:default>
      <AdminLoginForm v-if="isLoggedIn"/>
      <AdminPanelSelector @close-panel="isActive = !isActive"/>
    </template>
  </v-dialog>
</template>

<style scoped>

</style>
