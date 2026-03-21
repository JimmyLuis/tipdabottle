<template>
  <router-view/>
  <GlobalSnackBarErrorNotification/>
  <GlobalSnackBarNotification/>
  <GlobalSnackBarValidationNotification/>
</template>

<script setup>
  //
import Default from "@/layouts/default.vue";
  import GlobalSnackBarErrorNotification from "@/components/snackbar/GlobalSnackBarErrorNotification.vue";
  import GlobalSnackBarNotification from "@/components/snackbar/GlobalSnackBarNotification.vue";
  import GlobalSnackBarValidationNotification from "@/components/snackbar/GlobalSnackBarValidationNotification.vue";
</script>
