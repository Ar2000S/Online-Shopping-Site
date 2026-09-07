<script>
import { useAuthStore } from '@/stores/authStore.js'
import AppHeader from '@/components/AppHeader.vue'

export default {
  name: 'PurchaseConfirmView',
  components: { AppHeader },
  data() {
    return {
      auth: useAuthStore(),
      items: [],
      message: '',
      loaded: false
    }
  },
  computed: {
    sortedItems() {
      return [...this.items].sort((a, b) => a.productCode.localeCompare(b.productCode))
    },
    subtotal() {
      return this.items.reduce((sum, i) => sum + i.unitPrice * i.quantity, 0)
    },
    tax() {
      return Math.floor(this.subtotal * 10 / 100)
    },
    total() {
      return this.subtotal + this.tax
    }
  },
  async created() {
    const res = await fetch('/api/cart', { credentials: 'include' })
    if (res.ok) {
      this.items = await res.json()
    }
    this.loaded = true
  },
  methods: {
    formatPrice(price) {
      return '¥' + Number(price).toLocaleString()
    },
    async onPlaceOrder() {
      this.message = ''

      // Login verification (Return Screen Determination A)
      if (!this.auth.isLoggedIn) {
        this.$router.push({ name: 'member-login', query: { redirect: 'cart-confirm' } })
        return
      }

      const res = await fetch('/api/order/place', {
        method: 'POST',
        credentials: 'include'
      })
      const data = await res.json()

      if (res.status === 401 && data.code === 'LOGIN_REQUIRED') {
        this.$router.push({ name: 'member-login', query: { redirect: 'cart-confirm' } })
        return
      }
      if (!res.ok) {
        this.message = data.message
        return
      }

      this.$router.push('/cart/complete')
    },
    async onCancelShopping() {
      await fetch('/api/cart/clear', { method: 'POST', credentials: 'include' })
      this.$router.push('/')
    },
    onBack() {
      this.$router.push('/cart')
    }
  }
}
</script>

<template>
  <div v-if="loaded">
    <AppHeader />
    <h2>Purchase Confirmation</h2>
    <p v-if="message" class="error">{{ message }}</p>

    <table v-if="items.length > 0">
      <thead>
        <tr>
          <th>Product Code</th>
          <th>Product Name</th>
          <th>Seller</th>
          <th>Price</th>
          <th>Quantity</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in sortedItems" :key="item.productCode">
          <td>{{ item.productCode }}</td>
          <td>{{ item.productName }}</td>
          <td>{{ item.maker }}</td>
          <td class="price">{{ formatPrice(item.unitPrice) }}</td>
          <td>{{ item.quantity }}</td>
        </tr>
      </tbody>
    </table>

    <div class="fees">
      <div class="row"><label>Subtotal</label><span class="price">{{ formatPrice(subtotal) }}</span></div>
      <div class="row"><label>Sales Tax</label><span class="price">{{ formatPrice(tax) }}</span></div>
      <div class="row"><label>Total Amount</label><span class="price">{{ formatPrice(total) }}</span></div>
    </div>

    <div class="buttons">
      <button @click="onCancelShopping">Cancel Purchase</button>
      <button @click="onPlaceOrder">Place Order</button>
      <button @click="onBack">Back</button>
    </div>
  </div>
</template>

<style scoped>
.price { text-align: right; }
table { border-collapse: collapse; width: 100%; }
th, td { padding: 6px; border: 1px solid #ccc; text-align: left; }
</style>