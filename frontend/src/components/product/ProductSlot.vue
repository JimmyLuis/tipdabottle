<script setup>

import {onBeforeUnmount, onMounted, ref, watch} from "vue";

const emit = defineEmits(['increment', 'decrement'])

const props = defineProps({
  product: {
    type: Object,
    required: true,
    default: {
      id: 0,
      name: "NOT AVAILABLE",
      description: "NOT AVAILABLE",
      price: 0.00,
      category:"NOT AVAILABLE",
      stock: 1,
      container: {}
    }
  }
})

onBeforeUnmount(() => {
  bool.value = false
})

const bool = ref()
watch(props.product, (value, oldValue) => {
  bool.value = props.product.index > 0
})

let lastClick = 0

function handleClick(emitValue) {
  const now = Date.now()

  if (now - lastClick < 100) return // block doppelte taps

  lastClick = now
  emit(emitValue+"")
}
</script>

<template>
  <v-card class="rounded-lg" elevation="4" :ripple="true">
    <v-row
      class="justify-center align-center d-flex pl-5"
      >
      <v-col
        class="d-flex justify-center align-center pb-0"
        cols="2">
        <v-avatar
          size="56"
          class="border d-flex justify-center align-center elevation-3">
          <v-icon class="ma-3" size="40" icon="mdi-cup"></v-icon>
        </v-avatar>
      </v-col>
      <v-col
        cols="10"
        class="ml-0 pb-0">
        <v-card-item>
          <v-row no-gutters
                 class="align-center justify-center d-flex">
            <v-col
              cols="9">
              <v-card
                elevation="0"
                :title="product.name"
                :subtitle="product.description">
              </v-card>
            </v-col>
            <v-col
              cols="3">
              <div class="text-h6">{{product.price.toFixed(2)}} €</div>
            </v-col>
          </v-row>
        </v-card-item>
      </v-col>
    </v-row>
    <v-row class="d-flex justify-center">
      <v-col >
        <v-slide-x-transition hide-on-leave>
          <v-sheet v-if="!bool" color="secondary" class="d-flex flex-grow-1 justify-center py-2">
            <v-icon icon="mdi-plus" color="white"></v-icon>
          </v-sheet>

        </v-slide-x-transition>
        <v-slide-x-transition hide-on-leave>
          <v-row v-if="bool" no-gutters>
            <v-col cols="12" sm="8" >
              <v-sheet color="secondary" class="d-flex flex-grow-1 justify-center py-2"  @click.stop.prevent="handleClick('increment')" v-ripple="true">
                <v-icon icon="mdi-plus" color="white"></v-icon>
              </v-sheet>
            </v-col>
            <v-col cols="12" sm="4" >
              <v-sheet color="primary" class="d-flex flex-grow-1 justify-center py-2" @click.stop.prevent="handleClick('decrement')" v-ripple="true">
                <v-icon icon="mdi-minus" color="white"></v-icon>
              </v-sheet>
            </v-col>
          </v-row>
        </v-slide-x-transition>
      </v-col>
    </v-row>
  </v-card>




</template>

<style scoped>

</style>
