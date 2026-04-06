<script setup>

import {ref} from "vue";

const emit = defineEmits(['purchaseSelect'])

const props = defineProps({
  purchase: {
    type: Object,
    required: true,
    default: {
      id: 1,
      purchaseGroupId: 1,
      product: {
        id: 1,
        name: "Dummy",
        description: "Dummy Description",
        category: "dummy category",
        container: null,
        stock: 0,
      },
      customerId: 1,
      creationTime: Date.now(),
      worth: 1,
      quantity: 1,
      reversed: false,
      reversedReference: null,
      reversedGroupReference: null
    }
  }
})

const isSelected = ref(false)

let lastClick = 0
function handleClick() {
  const now = Date.now()
  if (now - lastClick < 100) return // block doppelte taps
  lastClick = now
  isSelected.value = !isSelected.value
  emit('purchaseSelect', props.purchase)

}

function calculateVCardColor(){
  if (props.purchase.reversed || !!props.purchase.reversedReference || !!props.purchase.reversedGroupReference){
    return 'primary-lighten-4'
  }
  return isSelected.value ? 'primary-accent-1' : 'grey-lighten-2'
}

function calculateVCardDisabled(){
  return props.purchase.reversed || !!props.purchase.reversedReference || !!props.purchase.reversedGroupReference
}

</script>

<template>
  <v-card class="pl-3" :disabled="calculateVCardDisabled()" :elevation="isSelected ? 1 : 3" :color="calculateVCardColor()" >
    <v-row>
      <v-col
        cols="12"
        sm="4">
        <v-card-title class="d-flex">
          <div class="d-sm-none pr-1">{{props.purchase.quantity}}x</div>
          {{props.purchase.product.name}}
        </v-card-title>
        <v-card-subtitle>
          {{props.purchase.product.description}}
        </v-card-subtitle>
      </v-col>
      <v-divider vertical />
      <v-col class=" d-none d-sm-inline"
             cols="12"
             sm="6">
        <v-card-title>
          Menge
        </v-card-title>
        <v-card-subtitle>
          <span>{{props.purchase.quantity}}</span><span class="pr-2">x</span>
          <span>{{props.purchase.product.price.toFixed(2)}}</span><span> €</span>
        </v-card-subtitle>
      </v-col>
      <v-divider vertical/>
      <v-col
        class="d-flex align-end justify-end"
        cols="12"
        sm="2">
        <div v-if="!calculateVCardDisabled()">
          <v-fade-transition hide-on-leave>
            <v-sheet v-if="!isSelected" @click="handleClick()" class="border-lg rounded elevation-3 mr-5" color="secondary">
              <v-icon size="35" icon="mdi-arrow-u-left-top"></v-icon>
            </v-sheet>
          </v-fade-transition>
          <v-fade-transition hide-on-leave>
            <v-sheet v-if="isSelected" @click="handleClick()" class="border-lg rounded elevation-3 mr-5" color="primary">
              <v-icon size="35" icon="mdi-close-circle-outline"></v-icon>
            </v-sheet>
          </v-fade-transition>
        </div>
        <div v-else>
          <v-sheet class="border-lg rounded elevation-3 mr-5 mt-1 d-flex align-center" color="secondary">
            <h4 class="mx-2 d-md-flex d-none ">Erstattet</h4>
            <v-icon size="35" icon="mdi-autorenew"></v-icon>
          </v-sheet>
        </div>
      </v-col>
    </v-row>
    <v-card-text class="px-0">
      <v-divider/>
      <v-row class="mt-1">
        <v-col
          cols="12"
          sm="4">
          <div class="text-h6">Gesamtwert: </div>
        </v-col>
        <v-col
          cols="12"
          sm="6">
          <div class="text-h6">{{purchase.worth.toFixed(2)}} €</div>
        </v-col>
        <v-col
          cols="12"
          sm="2">
          <v-spacer></v-spacer>
        </v-col>
      </v-row>
    </v-card-text>

  </v-card>
</template>

<style scoped>

</style>
