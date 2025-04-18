<script setup>

import ItemComponent from "@/components/ItemComponent.vue";
const myObject = {
  name: "Getraenk",
  picturepath: "src/assets/logo.png",
  price: "1€"
}
</script>
<template>
  <v-virtual-scroll
    :height="500"
    :items="['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30']"

  >
    <template v-slot:default="{ item }">
      <v-row justify="space-around" no-gutters>
        <v-col cols="auto" class="mt-3 mb-3"><ItemComponent /></v-col>
        <v-col cols="auto" class="mt-3 mb-3"><ItemComponent /></v-col>
        <v-col cols="auto" class="mt-3 mb-3"><ItemComponent /></v-col>
      </v-row>

    </template>
  </v-virtual-scroll>
</template>


<style scoped>

</style>
