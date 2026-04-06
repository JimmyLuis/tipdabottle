<script setup>

import BasicDialogCard from "@/components/common/card/BasicDialogCard.vue";
import CloseSubmitBtn from "@/components/common/card/actions/CloseSubmitBtn.vue";
import BasicCustomerInfo from "@/components/customer/BasicCustomerInfo.vue";
import SelectedProductList from "@/components/product/misc/SelectedProductList.vue";
import {ref, watch} from "vue";
import {useNotifyStore, useNotifyValidationStore} from "@/stores/app.js";
import ProductGrid from "@/components/product/ProductGrid.vue";
import {purchaseProducts} from "@/api/purchaseApi.js";

const emit = defineEmits(['close', 'submit'])
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

const purchase = ref(new Map)
const purchaseWorth = ref(0.0)
const purchaseViewList = ref([])
const productMap = ref(new Map)

watch(
  () => props.products,
  (products) => {
    products.forEach(p => {
      if (p.index == null) p.index = 0
    })
    if (productMap.value.size <= 0){
      productMap.value = new Map(props.products.map(product => [product.id, product]))
    }
  },
  { immediate: true }
)

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

async function submitPurchase() {
  if (purchase.value.values().toArray().length <= 0) {
    useNotifyValidationStore().set('Du musst mindestens ein Getränk bestellen!')
    return
  }
  await purchaseProducts(props.customer.id, purchase.value.values().toArray())
    .then(res => {
      if (res)
        useNotifyStore().set(`Vielen Dank für deine Bestellung! Dein Konto wurde angepasst ;)`)
    })
  props.products.forEach(p => {
    p.index = 0
  })
  emit('submit')
}

</script>

<template>
  <BasicDialogCard>
    <template #header>
      <div class="d-flex">
        <BasicCustomerInfo :customer>
          <template #below-balance>
            <div class="d-inline">
              <v-slide-x-transition hide-on-leave>
                <h4 v-if="purchaseWorth > 0.0" class="text-primary-darken-2" :key="purchaseWorth">- {{purchaseWorth.toFixed(2)}} €</h4>
              </v-slide-x-transition>
              <h4 v-if="!purchaseWorth > 0.0" class="text-transparent">- {{purchaseWorth.toFixed(2)}} €</h4>
            </div>
          </template>
        </BasicCustomerInfo>
        <v-spacer/>
        <SelectedProductList :product-list="purchaseViewList" />
        <v-spacer/>
        <v-spacer/>
      </div>
    </template>
    <template #header-actions>
      <CloseSubmitBtn @close="emit('close')" @submit="submitPurchase()" class="justify-end"/>
    </template>
    <template #body>
      <ProductGrid :products :disabled-customer="customer.locked" @update-purchase="handleProductUpdate"/>
    </template>
  </BasicDialogCard>
</template>

<style scoped>

</style>
