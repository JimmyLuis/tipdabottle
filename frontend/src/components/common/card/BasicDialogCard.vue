<script setup>

defineProps(['height', 'width'])

</script>

<template>
  <v-card :height :width >
    <v-card-title>
      <v-row no-gutters class="mb-1">
        <v-col
          cols="12"
          sm="10">
          <slot name="header">
            <div class="bg-purple-lighten-4">Header</div>
          </slot>
        </v-col>
        <v-col
          cols="12"
          sm="2">
          <slot name="header-actions">
            <div class="bg-purple-lighten-3">Actions</div>
          </slot>
        </v-col>
      </v-row>
    </v-card-title>
    <v-divider thickness="2" class="mx-3"></v-divider>
    <v-card-item class="pr-1 mb-1">
      <slot name="body"><div class="bg-purple-lighten-2 py-16">BODY</div></slot>
    </v-card-item>
  </v-card>
</template>

<style scoped>

</style>
