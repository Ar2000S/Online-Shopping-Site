<script>
import AppHeader from '@/components/AppHeader.vue'

export default {
  name: 'HistoryDeleteConfirmView',
  components: { AppHeader },
  data() {
    return { detail: null, error: '', loaded: false }
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
    async onExecute() {
      this.error = ''
      const collectNo = this.$route.params.collectNo
      const res = await fetch(`/api/history/${collectNo}`, {
        method: 'DELETE',
        credentials: 'include'
      })
      const data = await res.json()
      if (!res.ok) {
        this.error = data.message || 'Deletion failed'
        return
      }
      this.$router.push('/history/deleted')
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
    <p v-if="error" class="error">{{ error }}</p>

    <div class="generic-div">
      <h3 style="background-color: aquamarine;">Member Information</h3>
      <div class="row"><label>Member No.: </label><span>{{ detail.memberNo }}</span></div>
      <div class="row"><label>Name: </label><span>{{ detail.memberName }}</span></div>
      <div class="row"><label>Phone: </label><span>{{ detail.tel }}</span></div>
    </div>

    <h2 class="alert">_______Do you REALLY want to DELETE this?_______</h2>
    <div class="section">
      <h3 style="background-color: aquamarine;">Order Information</h3>
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
      <button @click="onExecute">Execute</button>
      <button @click="onBack">Back</button>
    </div>
  </div>
</template>

<style scoped>
.price { text-align: right; }
.section { margin-bottom: 20px; }
table { border-collapse: collapse; width: 100%; }
th, td { padding: 6px; border: 1px solid #f8a662; text-align: left; }
.alert {background-color: #fc5507; font-weight: 600; font-style: italic;}
.generic-div{ padding: 6px; border: 2px solid #f5e728; display: flex; gap: 4px; flex-direction: column;}
</style>