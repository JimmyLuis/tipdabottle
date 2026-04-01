<script setup>
import {onMounted, ref} from "vue";
import {getAllProducts} from "@/api/productApi.js";
import ProductAdminSlot from "@/components/product/admin/ProductAdminSlot.vue";
import ProductAdminAddSlot from "@/components/product/admin/add/ProductAdminAddSlot.vue";


const products = ref([]);
const productsDummy = ref([]);



onMounted(async () => {
  for (let i = 0; i < 10; i++) {
    let product = {
      id: i
    }
    productsDummy.value.push(product);
  }
  products.value = await getAllProducts();
})

const refreshAllProducts = async () => {
  products.value = await getAllProducts();
}

</script>

<template>
  <v-container fluid class="px-5">
    <v-row no-gutters v-if="products.length > 0">
      <v-col cols="12" sm="12">
        <ProductAdminAddSlot @refresh-all-products="refreshAllProducts"/>
      </v-col>
      <v-col
        v-for="product in products"
        :key="product.id"
        cols="12"
        sm="4"
      >
        <v-sheet class="ma-2 pa-2">
          <ProductAdminSlot v-if="products" :product/>
        </v-sheet>
      </v-col>
    </v-row>
    <v-row v-else no-gutters>
      <v-col
        v-for="product in productsDummy"
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
