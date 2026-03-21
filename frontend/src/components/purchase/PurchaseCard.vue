<script setup>
import ProductGrid from "@/components/product/ProductGrid.vue";
import {ref} from "vue";
import {useNotifyValidationStore} from "@/stores/app.js";

const props = defineProps({
  customer: {
    type: Object,
    required: true,
    default: {
      firstName: "NOT AVAILABLE",
      lastName: "NOT AVAILABLE",
      balance: 0.00
    }
  },
  products: {
    type: Array,
    required: true
  }
})

const emit = defineEmits(['cancelPurchase', 'submitPurchase'])

const purchase = ref(new Map)
const purchaseWorth = ref(0.0)
const purchaseViewList = ref([])
const valStore = useNotifyValidationStore()
const productMap = ref(new Map(props.products.map(product => [product.id, product])))

const handleProductUpdate = (product) => {
  if (product.index <= 0) {
    purchase.value.delete(product.id)
    purchaseViewList.value = purchase.value.values().toArray()
    purchaseViewList.value.reverse()
  } else {
    let newProduct = {
      id: product.id,
      name: product.name,
      quantity: product.index
    }
    purchase.value.set(product.id, newProduct)
    purchaseViewList.value = purchase.value.values().toArray()
    purchaseViewList.value.reverse()
  }

  purchaseWorth.value = 0;
  purchaseViewList.value.forEach(purchase => {
    let curProd = productMap.value.get(purchase.id)
    purchaseWorth.value = purchaseWorth.value + curProd.price * curProd.index
  })
}

function submitPurchase(){
  if (purchase.value.values().toArray().length <= 0) {
    valStore.set('Du musst mindestens ein Getränk bestellen!')
    return
  }
  emit('submitPurchase', purchase.value)
}

</script>

<template>
  <v-card>
    <div class="pl-4">
      <v-card-title class="pb-3">
        <v-row no-gutters>
          <v-col
            class="d-flex justify-space-between"
          cols="12"
          sm="8">
            <div class="pr-3">
              {{props.customer?.lastName}}<span v-if="!!props.customer.lastName">,</span>
              {{props.customer?.firstName}}
              <v-card-subtitle>
                <div class="d-flex justify-start align-center pb-1">
                  <div class="d-flex">
                    <h3 class="pr-3"> Saldo: </h3>
                    <div class="d-inline">
                      <h3 class="justify-end d-flex">{{props.customer.balance.toFixed(2)}} €</h3>
                      <v-slide-x-transition hide-on-leave>
                        <h3 v-if="purchaseWorth > 0.0" class="text-red-darken-2" :key="purchaseWorth">- {{purchaseWorth.toFixed(2)}} €</h3>
                      </v-slide-x-transition>
                      <h3 v-if="!purchaseWorth > 0.0" class="text-transparent">- {{purchaseWorth.toFixed(2)}} €</h3>
                    </div>
                  </div>
                  <v-spacer></v-spacer>
                </div>
              </v-card-subtitle>
            </div>
            <v-spacer/>
            <v-card max-height="80" class="overflow-y-auto scroll-container"
            elevation="0">
              <v-card-item class="text-body-2">
                <v-slide-x-reverse-transition :hide-on-leave="true" group>
                  <div v-for="product in purchaseViewList" :key="product.id" class="scroll-content pb-1 justify-end d-flex">
                    {{product.quantity}}x {{product.name}}
                  </div>
                  <div v-if="purchaseViewList.length <= 0" class="text-transparent ">
                    tests
                  </div>
                </v-slide-x-reverse-transition>
              </v-card-item>
            </v-card>
            <v-spacer/>
          </v-col>
          <v-col
            class="d-flex justify-md-end justify-sm-center"
            cols="12"
          sm="4">
            <div class="pr-2 d-flex align-center pb-8">
              <v-btn class="mr-3" color="red" icon="mdi-close" @click="emit('cancelPurchase')"></v-btn>
              <v-divider vertical thickness="1" opacity="0.15" color=""></v-divider>
              <v-btn class="ml-3" color="green" icon="mdi-arrow-right" @click="submitPurchase()"></v-btn>
            </div>
          </v-col>
        </v-row>

      </v-card-title>
    </div>
    <v-card-text>
      <v-banner class="pt-0"></v-banner>
    </v-card-text>
    <v-card-item class="pt-0 px-0" >
      <ProductGrid :products :disabled-customer="customer.locked" @update-purchase="handleProductUpdate"/>
    </v-card-item>
  </v-card>
</template>

<style scoped>
.scroll-container {
  position: relative;
  top: 20px;
  max-height: 75px;
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
