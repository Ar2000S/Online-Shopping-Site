<script>
import AppHeader from '@/components/AppHeader.vue'

export default {
  name: 'HistoryDetailsView',
  components: { AppHeader },
  data() {
    return { detail: null, loaded: false }
  },
  async created() {
    const collectNo = this.$route.params.collectNo
    const res = await fetch(`/api/history/${collectNo}`, { credentials: 'include' })
    if (res.ok) {
      this.detail = await res.json()
    }
    this.loaded = true
  },
  methods: {
    formatPrice(price) {
      return '¥' + Number(price).toLocaleString()
    },
    onBack() {
      this.$router.push('/history')
    }
  }
}
</script>

<template>
  <div v-if="loaded && detail">
    <AppHeader />
    <h2>Purchase History Details</h2>

    <div class="section">
      <h3>Member Information</h3>
      <div class="row"><label>Member No.: </label><span>{{ detail.memberNo }}</span></div>
      <div class="row"><label>Name: </label><span>{{ detail.memberName }}</span></div>
      <div class="row"><label>Phone: </label><span>{{ detail.tel }}</span></div>
    </div>

    <div class="section">
      <h3>Order Information</h3>
      <div class="row"><label>Order No.: </label><span>{{ detail.collectNo }}</span></div>
      <div class="row"><label>Order Date: </label><span>{{ detail.orderDate }}</span></div>
      <div class="row"><label>Subtotal: </label><span class="price">{{ formatPrice(detail.subtotal) }}</span></div>
      <div class="row"><label>Sales Tax: </label><span class="price">{{ formatPrice(detail.tax) }}</span></div>
      <div class="row"><label>Total Amount: </label><span class="price">{{ formatPrice(detail.total) }}</span></div>
    </div>

    <table>
      <thead>
        <tr><th>Product Code</th><th>Product Name</th><th>Seller</th><th>Price</th><th>Quantity</th></tr>
      </thead>
      <tbody>
        <tr v-for="item in detail.items" :key="item.productCode">
          <td>{{ item.productCode }}</td>
          <td>{{ item.productName }}</td>
          <td>{{ item.maker }}</td>
          <td class="price">{{ formatPrice(item.price) }}</td>
          <td>{{ item.quantity }}</td>
        </tr>
      </tbody>
    </table>

    <div class="buttons">
      <button @click="onBack">Back</button>
    </div>
  </div>
</template>

<style scoped>
.price { text-align: right; }
.section { margin-bottom: 20px; }
table { border-collapse: collapse; width: 100%; }
th, td { padding: 6px; border: 1px solid #ccc; text-align: left; }
</style>
