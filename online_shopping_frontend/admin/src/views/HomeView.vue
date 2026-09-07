<script>
import { useAuthStore } from '@/stores/authStore.js'
import AppHeader from '@/components/AppHeader.vue'

export default {
  name: 'HomeView',
  components: { AppHeader },
  data() {
    return { auth: useAuthStore() }
  },
  methods: {
    onPurchaseHistory() {
      this.$router.push('/history')
    },
    async onLogout() {
      await this.auth.logout()
      this.$router.push('/staff-login')
    }
  }
}
</script>

<template>
  <div><h2 class="page-title">Administrator Menu</h2></div><div>
    
    <div class="card">
      <AppHeader />
      <nav class="menu-links">
        <div style="background-color: orange;" @click.prevent="onPurchaseHistory">Check Purchase Records</div>
      </nav>
    </div>
  </div>
</template>

<style scoped>
.page-title {
  font-size: 24px;
  font-weight: bold;
  color: #475569;
  margin: 0 0 20px 0;   
  padding-bottom: 8px;
  border-bottom: 2px solid #cbd5e1;
}

.card {
  background: rgba(255, 255, 255, 0.85);
  border-radius: 8px;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  max-width: 400px;         /* the card stays narrow */
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.menu-links {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
</style>