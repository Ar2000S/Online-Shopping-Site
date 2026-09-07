<script>
import AppHeader from '@/components/AppHeader.vue'

export default {
  name: 'ProductDetailsView',
  components: { AppHeader },
  data() {
    return {
      product: null,
      quantity: 1,
      message: '',
      loaded: false
    }
  },
  async created() {
    const code = this.$route.params.code
    const res = await fetch(`/api/products/${code}`, { credentials: 'include' })
    if (res.ok) {
      this.product = await res.json()
    }
    this.loaded = true
  },
  methods: {
    formatPrice(price) {
      return '¥' + Number(price).toLocaleString()
    },
    async onAddToCart() {
      this.message = ''
      const res = await fetch('/api/cart/add', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify({
          productCode: this.product.productCode,
          quantity: Number(this.quantity)
        })
      })
      const data = await res.json()
      if (!res.ok) {
        this.message = data.message
        return
      }
      this.$router.push('/cart')
    },
    onBack() {
      this.$router.push('/product-search')
    }
  }
}
</script>

<template>
  <div v-if="loaded">
    <AppHeader />
    <h2 v-if="product">{{ product.productName }}</h2>
    <p v-if="message" class="error">{{ message }}</p>

    <div v-if="product">
      <img v-if="product.pictureName" :src="`/${product.pictureName}`" alt="" width="200" />
      <p>{{ product.memo }}</p>
      <p class="price">{{ formatPrice(product.unitPrice) }}</p>

      <div class="row">
        <label>Quantity</label>
        <input type="text" v-model="quantity" />
      </div>

      <div class="buttons">
        <button @click="onAddToCart">Add to Cart</button>
        <button @click="onBack">Back</button>
      </div>
    </div>

    <p v-else>Product not found.</p>
  </div>
</template>

<style scoped>
.price { text-align: right; font-weight: bold; }
</style>