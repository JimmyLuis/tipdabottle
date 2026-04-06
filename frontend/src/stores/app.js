// Utilities
import {defineStore} from 'pinia'
import {getSession, login, logout} from "@/api/accountApi.js";
import {getAllCustomers} from "@/api/customerApi.js";
import {getAllPurchases} from "@/api/purchaseApi.js";
import {getAllProducts} from "@/api/productApi.js";

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

export const useCustomerStore = defineStore('customer', {
  state: () => ({
    customers: [],
    loaded: false,
    loading: false
  }),

  actions: {
    async fetchCustomers() {
      if (this.loading) return
      this.loading = true
      try {
        let res = await getAllCustomers()
        res.sort((a, b) => {
          if (b.community !== a.community) {
            return b.community - a.community
          }
          return a.lastName.localeCompare(b.lastName)
        })
        this.customers = res
        this.loaded = true
      } finally {
        this.loading = false
      }
    },

    clear() {
      this.customers = []
      this.loaded = false
    }
  }
})

export const useProductStore = defineStore('product', {
  state: () => ({
    products: [],
    loaded: false,
    loading: false
  }),

  actions: {
    async fetchProducts() {
      if (this.loading) return

      this.loading = true
      try {
        this.products = await getAllProducts()
        this.loaded = true
      } finally {
        this.loading = false
      }
    },

    clear() {
      this.products = []
      this.loaded = false
    }
  }
})
