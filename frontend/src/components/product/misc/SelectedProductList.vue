<script setup>
const props = defineProps({
  productList: {
    type: Array,
    required: true,
    default: [
      {
        id: 1,
        name: "ColaDefault",
        quantity: 3
      },
      {
        id: 2,
        name: "FantaDefault",
        quantity: 21
      },
      {
        id: 3,
        name: "SpriteDefault",
        quantity: 3
      },
      {
        id: 4,
        name: "MezzoMixDefault",
        quantity: 1
      }
    ]
  }
})
</script>

<template>
  <v-card height="90" class="overflow-y-auto scroll-container"
          elevation="0">
    <v-card-item class="text-body-2">
      <v-slide-x-reverse-transition :hide-on-leave="true" group>
        <div v-for="product in productList" :key="product.id" class="scroll-content pb-1 justify-end d-flex">
          {{product.quantity}}x {{product.name}}
        </div>
      </v-slide-x-reverse-transition>
    </v-card-item>
  </v-card>
</template>

<style scoped>

.scroll-container {
  position: relative;
  top: 20px;
  max-height: 95px;
  overflow-y: auto;
}

/* Fade unten */
.scroll-container::after {
  content: "";
  position: sticky;
  bottom: 0;
  display: block;
  height: 50px;

  background: linear-gradient(
    to bottom,
    rgba(255, 255, 255, 0),
    rgba(255, 255, 255, 1)
  );
}
</style>
