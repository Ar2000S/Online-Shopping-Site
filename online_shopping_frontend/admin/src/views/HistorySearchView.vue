<script>
import AppHeader from '@/components/AppHeader.vue'
import { validateRec101 } from '@/utils/rec101Validation'

export default {
  name: 'HistorySearchView',
  components: { AppHeader },
  data() {
  return {
    filters: {
      memberNo: '',
      memberName: '',
      startYear: '', startMonth: '', startDay: '',
      endYear: '', endMonth: '', endDay: '',
      totalLower: '', totalUpper: ''
    },
    orders: [],
    currentPage: 0,
    totalPages: 0,
    message: '',
    hasSearched: false,
    errors: {}         
  }
},
  computed: {
    years() {
      const current = new Date().getFullYear()
      return Array.from({ length: 10 }, (_, i) => current - i)
    },
    months() { return Array.from({ length: 12 }, (_, i) => i + 1) },
    days() { return Array.from({ length: 31 }, (_, i) => i + 1) }
  },
  methods: {
    formatPrice(price) {
      return '¥' + Number(price).toLocaleString()
    },
    buildDate(y, m, d) {
      if (!y || !m || !d) return ''
      const pad = (n) => String(n).padStart(2, '0')
      return `${y}-${pad(m)}-${pad(d)}`
    },
    async doSearch(page = 0) {
      this.errors = validateRec101(this.filters)
      if (Object.keys(this.errors).length > 0) {
             return
             }
      this.message = ''
      const params = new URLSearchParams()
      if (this.filters.memberNo) params.set('memberNo', this.filters.memberNo)
      
      if (this.filters.memberName) params.set('memberName', this.filters.memberName)
      const start = this.buildDate(this.filters.startYear, this.filters.startMonth, this.filters.startDay)
      const end = this.buildDate(this.filters.endYear, this.filters.endMonth, this.filters.endDay)
      if (start) params.set('startDate', start)
      if (end) params.set('endDate', end)
      if (this.filters.totalLower) params.set('totalLower', this.filters.totalLower)
      if (this.filters.totalUpper) params.set('totalUpper', this.filters.totalUpper)
      params.set('page', page)

      const res = await fetch(`/api/history?${params.toString()}`, { credentials: 'include' })
      const data = await res.json()

      this.orders = data.orders
      this.currentPage = data.currentPage
      this.totalPages = data.totalPages
      this.hasSearched = true
      if (data.code === 'MSG013') this.message = data.message
    },
    onClear() {
      this.filters = {
        memberNo: '', memberName: '',
        startYear: '', startMonth: '', startDay: '',
        endYear: '', endMonth: '', endDay: '',
        totalLower: '', totalUpper: ''
      }
      this.orders = []
      this.hasSearched = false
      this.message = ''
    },
    goToPage(page) {
      if (page < 0 || page >= this.totalPages) return
      this.doSearch(page)
    },
    onDetails(collectNo) {
      this.$router.push(`/history/${collectNo}`)
    },
    onDelete(collectNo) {
      this.$router.push(`/history/${collectNo}/delete`)   // REC201 — not built yet
    },
    onMenu() {
      this.$router.push('/')
    }
  }
}
</script>

<template>
  <div class="page-title" style=""><h2>Purchase History Search</h2></div>
  <div>
    <AppHeader />
    <p v-if="message" class="error">{{ message }}</p>

    <div class="filters">
      <div class="row">
        <label>Member No.: </label>
        <input type="text" v-model="filters.memberNo" />
        <span v-if="errors.memberNo" class="error">{{ errors.memberNo }}</span> <!--err msg, if invalid -->

      </div>
      <div class="row">
        <label>Member Name: </label>
        <input type="text" v-model="filters.memberName" />
      </div>
      <div class="row">
        <label>Order Date (Start)  </label>
        <select v-model="filters.startYear"><option value="">Year</option><option v-for="y in years" :key="y" :value="y">{{ y }}</option></select>
        <select v-model="filters.startMonth"><option value="">Month</option><option v-for="m in months" :key="m" :value="m">{{ m }}</option></select>
        <select v-model="filters.startDay"><option value="">Day</option><option v-for="d in days" :key="d" :value="d">{{ d }}</option></select>
      </div>
      <div class="row">
        <label>Order Date (End)  </label>
        <select v-model="filters.endYear"><option value="">Year</option><option v-for="y in years" :key="y" :value="y">{{ y }}</option></select>
        <select v-model="filters.endMonth"><option value="">Month</option><option v-for="m in months" :key="m" :value="m">{{ m }}</option></select>
        <select v-model="filters.endDay"><option value="">Day</option><option v-for="d in days" :key="d" :value="d">{{ d }}</option></select>
      </div>
      <div class="row">
        <label>Total Amount: </label>
        <input type="text" v-model="filters.totalLower" placeholder="lower" />
        <span v-if="errors.totalLower" class="error">{{ errors.totalLower }}</span>
        ~
        <input type="text" v-model="filters.totalUpper" placeholder="upper" />
        <span v-if="errors.totalUpper" class="error">{{ errors.totalUpper }}</span>
      </div>
      <div class="buttons">
        <button @click="doSearch(0)">Search</button>
        <button @click="onClear">Clear</button>
      </div>
    </div>

    <div v-if="hasSearched && orders.length > 0">
      <div class="paging">
        <button @click="goToPage(0)">&laquo;</button>
        <button @click="goToPage(currentPage - 1)">&lsaquo;</button>
        <span>{{ currentPage + 1 }} / {{ totalPages }}</span>
        <button @click="goToPage(currentPage + 1)">&rsaquo;</button>
        <button @click="goToPage(totalPages - 1)">&raquo;</button>
      </div>

      <table>
        <thead>
          <tr>
            <th>Order No.</th><th>Member No.</th><th>Member Name</th>
            <th>Order Date</th><th>Subtotal</th><th>Sales Tax</th><th>Total</th><th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="o in orders" :key="o.collectNo">
            <td><span class="link" @click="onDetails(o.collectNo)">{{ o.collectNo }}</span></td>
            <td>{{ o.memberNo }}</td>
            <td>{{ o.memberName }}</td>
            <td>{{ o.orderDate }}</td>
            <td class="price">{{ formatPrice(o.subtotal) }}</td>
            <td class="price">{{ formatPrice(o.tax) }}</td>
            <td class="price">{{ formatPrice(o.total) }}</td>
            <td><button @click="onDelete(o.collectNo)">Delete</button></td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="buttons">
      <button @click="onMenu">Back to Menu</button>
    </div>
  </div>
</template>

<style scoped>
.price { text-align: right; }
.link { color: #0066cc; cursor: pointer; text-decoration: underline; }
.paging { display: flex; gap: 10px; align-items: center; }
table { border-collapse: collapse; width: 100%; }
th, td { padding: 6px; border: 1px solid #ccc; text-align: left; }

.page-title {
  font-size: 12px;
  position: relative;
  font-weight: bold;
  color: #334155;
  background: rgba(154, 242, 248, 0.5); 
  padding: 10px 20px;
  margin: 0 0 50px 0;
  border-bottom: 2px solid #cbd5e1;
}
</style>


