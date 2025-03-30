<script setup>
import MemberSlot from "@/components/MemberSlot.vue";
</script>

<template>
  <v-container>
    <v-row no-gutters>
      <v-col
        v-for="n in 13"
        :key="n"
        cols="12"
        sm="4"
      >
        <v-sheet class="ma-2 pa-2">
          <member-slot />
        </v-sheet>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>

</style>
