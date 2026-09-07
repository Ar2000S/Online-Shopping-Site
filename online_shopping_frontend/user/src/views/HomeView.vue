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
    onNewMemberRegistration() {
      this.$router.push('/member-register')
    },
    onMemberInfoEditDelete() {
      if (!this.auth.isLoggedIn) {
        this.$router.push({ name: 'member-login', query: { redirect: 'member-info' } })
      } else {
        this.$router.push({ name: 'member-info' })
      }
    },
    onProductSearch() {
      this.$router.push('/product-search')
    },
    onShoppingCart() {
      this.$router.push('/cart')
    },
    onLogin() {
      this.$router.push({ name: 'member-login' })
    },
    async onLogout() {
      await this.auth.logout()
      this.$router.push({ name: 'member-login' })
    }
  }
}
</script>

<template>
  <div class="parent-container">
    <AppHeader />
    <h2>User Menu</h2>

    <nav class="menu-links">
      <router-link v-if="!auth.isLoggedIn" to="/member-register">New Member Registration</router-link>
      <a href="#" @click.prevent="onMemberInfoEditDelete">Member Information Edit/Delete</a>
      <a href="#" @click.prevent="onProductSearch">Product Search</a>
      <a href="#" @click.prevent="onShoppingCart">Shopping Cart</a>
    </nav>

    <div class="buttons">
      <button v-if="!auth.isLoggedIn" @click="onLogin">Login</button>
      <button v-if="auth.isLoggedIn" @click="onLogout">Logout</button>
    </div>
  </div>
</template>

<style scoped>
.parent-container {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.menu-links {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
</style>