<script>
import AppHeader from '@/components/AppHeader.vue'
import { validateSho101 } from '@/utils/sho101Validation'



export default {
  name: 'ProductSearchView',
  components: { AppHeader },
  data() {
    return {
      categories: [],
      filters: {
        categoryId: '',
        productName: '',
        maker: '',
        priceLower: '',
        priceUpper: ''
      },
      products: [],
      currentPage: 0,
      totalPages: 0,
      message: '',
      hasSearched: false,
      errors: {}  
    }
  },
  async created() {
    const res = await fetch('/api/products/categories', { credentials: 'include' })
    if (res.ok) this.categories = await res.json()
  },
  methods: {
    truncate(text) {
      if (!text) return ''
      return text.length > 20 ? text.slice(0, 20) + '...' : text
    },
    formatPrice(price) {
      return '¥' + Number(price).toLocaleString()
    },
    async doSearch(page = 0) {
      this.errors = validateSho101(this.filters)
      if (Object.keys(this.errors).length > 0) {
        return // invalid input retursn err
             }
      this.message = ''
      const params = new URLSearchParams()
      if (this.filters.categoryId) params.set('categoryId', this.filters.categoryId)
      if (this.filters.productName) params.set('productName', this.filters.productName)
      if (this.filters.maker) params.set('maker', this.filters.maker)
      if (this.filters.priceLower) params.set('priceLower', this.filters.priceLower)
      if (this.filters.priceUpper) params.set('priceUpper', this.filters.priceUpper)
      params.set('page', page)

      const res = await fetch(`/api/products?${params.toString()}`, { credentials: 'include' })
      const data = await res.json()

      this.products = data.products
      this.currentPage = data.currentPage
      this.totalPages = data.totalPages
      this.hasSearched = true

      if (data.code === 'MSG005') {
        this.message = data.message
      }
    },
    onClear() {
      this.filters = { categoryId: '', productName: '', maker: '', priceLower: '', priceUpper: '' }
      this.products = []
      this.hasSearched = false
      this.message = ''
    },
    goToPage(page) {
      if (page < 0 || page >= this.totalPages) return
      this.doSearch(page)
    },
    onProductClick(code) {
      this.$router.push(`/product-search/${code}`)
    },
    onMenu() {
      this.$router.push('/')
    },
    
  }
}
</script>

<template>
  <div>
    <AppHeader />
    <h2>Product Search</h2>
    <p v-if="message" class="error">{{ message }}</p>

    <div class="filters">
      <div class="row">
        <label>Category</label>
        <select v-model="filters.categoryId">
          <option value="">-- all --</option>
          <option v-for="c in categories" :key="c.ctgrId" :value="c.ctgrId">{{ c.name }}</option>
        </select>
      </div>
      <div class="row">
        <label>Product Name</label>
        <input type="text" v-model="filters.productName" />
      </div>
      <div class="row">
        <label>Seller</label>
        <input type="text" v-model="filters.maker" />
      </div>
      <div class="row">
        <label>Price</label>
        <input type="text" v-model="filters.priceLower" placeholder="lower" />
        <span v-if="errors.priceLower" class="error">{{ errors.priceLower }}</span> //error below the input
        ~
        <input type="text" v-model="filters.priceUpper" placeholder="upper" />
        <span v-if="errors.priceUpper" class="error">{{ errors.priceUpper }}</span> //err below input
      </div>
      <div class="buttons">
        <button @click="doSearch(0)">Search</button>
        <button @click="onClear">Clear</button>
      </div>
    </div>

    <div v-if="hasSearched && products.length > 0">
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
        <th>Product Code</th>
        <th>Product Name</th>
        <th>Seller</th>
        <th>Price</th>
        <th>Product Description</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="p in products" :key="p.productCode">
        <td>{{ p.productCode }}</td>
        <td><span class="link" @click="onProductClick(p.productCode)">{{ p.productName }}</span></td>
        <td>{{ p.maker }}</td>
        <td class="price">{{ formatPrice(p.unitPrice) }}</td>
        <td>{{ truncate(p.memo) }}</td>
      </tr>
    </tbody>
  </table>
</div>

    <div class="buttons">
      <button @click="onMenu">To Menu</button>
    </div>
  </div>
</template>

<style scoped>
.price { text-align: right; }
.paging { display: flex; gap: 10px; align-items: center; }
table { border-collapse: collapse; width: 100%; }
th, td { padding: 6px; border: 1px solid #ccc; text-align: left; }
.link {
  color: #0066cc;
  cursor: pointer;
  text-decoration: underline;
}
</style>