<script>
import { useAuthStore } from '@/stores/authStore.js'

export default {
  name: 'AppHeader',
  data() {
    return {
      auth: useAuthStore(),
      now: new Date()
    }
  },
  computed: {
    displayName() {
      return this.auth.isLoggedIn ? this.auth.userName : 'Guest'
    },
    formattedTime() {
      const d = this.now
      const pad = (n) => String(n).padStart(2, '0')
      return `${d.getFullYear()}/${pad(d.getMonth()+1)}/${pad(d.getDate())} ${pad(d.getHours())}:${pad(d.getMinutes())}`
    }
  },
  mounted() {
    this.interval = setInterval(() => { this.now = new Date() }, 1000 * 30)
  },
  beforeUnmount() {
    clearInterval(this.interval)
  }
}
</script>

<template>
  <div class="app-header">
    <span>{{ displayName }}</span>
    <span>{{ formattedTime }}</span>
  </div>
</template>
