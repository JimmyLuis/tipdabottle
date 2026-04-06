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
  import {useAuthStore} from "@/stores/app.js";
  import {onBeforeMount, onMounted} from "vue";
  import router from "@/router/index.js";

  onBeforeMount(async () => {
    await useAuthStore().authenticate()
    if (!useAuthStore().isAuthenticated) {
      router.push("/login")
      //await useAuthStore().login('admin@example.com', 'admin123')
    }
  })
</script>
