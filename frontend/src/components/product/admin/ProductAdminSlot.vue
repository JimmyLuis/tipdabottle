<script setup>
import {ref} from "vue";
import ProductAdminCard from "@/components/product/admin/ProductAdminCard.vue";

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

const isActive = ref(false);

</script>

<template>
  <v-dialog v-model="isActive">
    <template v-slot:activator="{props: activatorProps}">
      <v-card elevation="2" v-bind="activatorProps" variant="tonal" height="75" class="align-content-center v-card--hover" style="user-select: none">
        <v-row no-gutters class="d-flex align-center justify-center">
          <v-col
            class="d-flex  justify-center"
          cols="12"
          sm="3">
            <v-avatar
              class="elevation-1"
            icon="mdi-cup"
            />
          </v-col>
          <v-col
            class="d-flex align-center"
            cols="12"
            sm="9">
            <v-card-title>
              {{props.product.name}}
              <v-card-subtitle class="ma-0 pa-0"> {{props.product.description}}</v-card-subtitle>
            </v-card-title>
            <v-spacer/>
            <v-icon v-if="product.stock < 15" size="30" color="primary-lighten-1" class="pr-5" icon="mdi-alert"></v-icon>
          </v-col>
        </v-row>
      </v-card>
    </template>
    <template v-slot:default>
      <v-card height="700" >
        <v-card-item >
          <ProductAdminCard :product @cancel-edit="isActive= !isActive"/>
        </v-card-item>
      </v-card>
    </template>
  </v-dialog>
</template>

<style scoped>

</style>
