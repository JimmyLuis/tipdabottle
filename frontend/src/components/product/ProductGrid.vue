<script setup>

import {onBeforeUnmount, onMounted, ref} from "vue";
import ProductSlot from "@/components/product/ProductSlot.vue";


const props = defineProps({
  products: {
    type: Array,
    required: true
  }
})
const productDummy = ref([])
onMounted(async () => {
  for (let i = 0; i < 20; i++) {
    let product = {
      id: i
    }
    productDummy.value.push(product);
  }

  props.products.forEach(product => {
    product.index = 0;
  })
})

onBeforeUnmount(() => {
  props.products.forEach(product => {
    product.index = 0;
  })
})

function validateBadgeColor(product){
  if (product.index === 0) return "transparent"
  return "red"
}

function validateBadgeVision(product){
  return product.index === 0;

}
let lastClick = 0

function handleClick(index) {
  const now = Date.now()

  if (now - lastClick < 100) return index // block doppelte taps

  lastClick = now
  return index = index + 1
}
</script>
<template>
  <v-container fluid height="490" class="overflow-y-auto pt-0 mx-0 px-0">
    <v-row no-gutters v-if="props.products.length > 0">
      <v-col
        v-for="product in props.products"
        :key="product.id"
        cols="12"
        sm="6"
        class="d-flex pa-2"
      >
        <v-badge class="flex-grow-1 pr-5 pt-2"
                 style="user-select: none"
                 :color="validateBadgeColor(product)"
                 :dot="validateBadgeVision(product)"
                 :content="product.index"
        >
          <template v-slot:badge>
            <h3>{{product.index}}</h3>
          </template>
          <v-sheet class="ma-0 pa-0 flex-grow-1" style="user-select: none" >
            <ProductSlot @click.stop.prevent="product.index = handleClick(product.index)" v-if="props.products" :product
            @decrement="product.index--"
            @increment="product.index++"
            />
          </v-sheet>
        </v-badge>


      </v-col>
    </v-row>
    <v-row v-else no-gutters>
      <v-col
        v-for="product in productDummy"
        :key="product.id"
        cols="12"
        sm="4"
      >
        <v-sheet class="ma-2 pa-2">
          <v-skeleton-loader elevation="2" type="paragraph"></v-skeleton-loader>
        </v-sheet>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>

</style>
