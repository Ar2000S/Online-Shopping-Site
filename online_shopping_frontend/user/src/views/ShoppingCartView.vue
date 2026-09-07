<script>
import AppHeader from '@/components/AppHeader.vue'
import { SMSG } from '@/utils/validationMessages' //get err msgs
import { validateRec101 } from '@/utils/rec101Validation'

export default {
  name: 'ShoppingCartView',
  components: { AppHeader },
  data() {
    return {
      items: [],
      selected: [],
      message: '',
      loaded: false
    }
  },
  async created() {
    await this.loadCart()
  },
  methods: {
    async loadCart() {
      const res = await fetch('/api/cart', { credentials: 'include' })
      if (res.ok) {
        this.items = await res.json()
      }
      this.loaded = true
    },
    formatPrice(price) {
      return '¥' + Number(price).toLocaleString()
    },
    validateItems() {
      for (const item of this.items) {
        const qty = Number(item.quantity)
        if (!Number.isInteger(qty) || qty < 1 || qty > 999) {
          return { code: 'MSG007', productCode: item.productCode }
        }
        if (qty > item.stockCount) {
          return { code: 'MSG008', productCode: item.productCode }
        }
      }
      return null
    },
    async onCancel() {
      this.message = ''
      if (this.selected.length === 0) {
        this.message = SMSG.MSG009   // "Please select the item(s) to cancel."
        return
      }
      await fetch('/api/cart/remove', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify(this.selected)
      })
      this.selected = []
      await this.loadCart()
    },
    async onStopShopping() {
      await fetch('/api/cart/clear', { method: 'POST', credentials: 'include' })
      this.$router.push('/')
    },
    async onPlaceOrder() {
      this.message = ''
      if (this.items.length === 0) return

  const err = this.validateItems()
      if (err) {
      this.message = err.code
      return
  }

  // sync edited quantities to session cart before confirmation
  await fetch('/api/cart/update', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    credentials: 'include',
    body: JSON.stringify(this.items.map(i => ({
      productCode: i.productCode,
      quantity: Number(i.quantity)
    })))
  })

  this.$router.push('/cart/confirm')
},
    
    onBackToMenu() {
      this.$router.push('/')
    }
  }
}
</script>

<template>
  <div v-if="loaded">
    <AppHeader />
    <h2>Shopping Cart</h2>
    <p v-if="message" class="error">{{ message }}</p>

    <table v-if="items.length > 0">
      <thead>
        <tr>
          <th>Select</th>
          <th>Product Code</th>
          <th>Product Name</th>
          <th>Seller</th>
          <th>Price</th>
          <th>Purchase Quantity</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in items" :key="item.productCode">
          <td><input type="checkbox" :value="item.productCode" v-model="selected" /></td>
          <td>{{ item.productCode }}</td>
          <td>{{ item.productName }}</td>
          <td>{{ item.maker }}</td>
          <td class="price">{{ formatPrice(item.unitPrice) }}</td>
          <td><input type="text" v-model="item.quantity" class="qty" /></td>
        </tr>
      </tbody>
    </table>

    <p v-else>Your cart is empty.</p>

    <div class="buttons">
      <button @click="onCancel">Cancel</button>
      <button @click="onStopShopping">Cancel Purchase</button>
      <button @click="onPlaceOrder" :disabled="items.length === 0">Place Order</button>
      <button @click="onBackToMenu">Back to Menu</button>
    </div>
  </div>
</template>

<style scoped>
.price { text-align: right; }
.qty { width: 60px; }
table { border-collapse: collapse; width: 100%; }
th, td { padding: 6px; border: 1px solid #ccc; text-align: left; }
</style>