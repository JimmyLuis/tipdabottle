// Utilities
import { defineStore } from 'pinia'

export const useAppStore = defineStore('app', {
  state: () => ({
    //
  }),
})

export const useErrorStore = defineStore("errorStore", {
  state: () => ({
    message: {},
    show: false
  }),
  actions: {
    set (errorMessage) {
      this.message = errorMessage
      this.show = true
    },
    clear () {
      this.message = {}
      this.show = false
    }
  }
})
