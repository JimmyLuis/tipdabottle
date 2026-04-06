// Utilities
import {defineStore} from 'pinia'
import {getSession, login, logout} from "@/api/accountApi.js";

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

export const useNotifyStore = defineStore("notifyStore", {
  state: () => ({
    message: [],
    show: false
  }),
  actions: {
    set (message) {
      this.message = message
      this.show = true
    },
    clear () {
      this.message = {}
      this.show = false
    }
  }
})

export const useNotifyValidationStore = defineStore("notifyValidationStore", {
  state: () => ({
    message: {},
    show: false
  }),
  actions: {
    set (message) {
      this.message = message
      this.show = true
    },
    clear () {
      this.message = {}
      this.show = false
    }
  }
})

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null
  }),

  getters: {
    isAuthenticated: (state) => !!state.user,
    isAdmin: (state) => state.user && state.user.role === 'ROLE_ADMIN',
    getRole: (state) => state.user ? state.user.role : null,
    getRelatedCustomer: (state) => state.user ? state.user.customer : null
  },

  actions: {
    async login(email, password) {
      this.user = await login(email, password)
    },
    async logout() {
      await logout()
      this.user = null
    },
    async authenticate() {
      this.user = await getSession()
    }
  }
})
