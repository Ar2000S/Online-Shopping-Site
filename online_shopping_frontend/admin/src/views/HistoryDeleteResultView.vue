<script>
import AppHeader from '@/components/AppHeader.vue'

export default {
  name: 'HistoryDeleteResultView',
  components: { AppHeader },
  data() {
    return { message: 'Purchase history deletion complete.' }
  },
  methods: {
    onSearch() {
      this.$router.push('/history')
    },
    onMenu() {
      this.$router.push('/')
    }
  }
}
</script>

<template>
  <div>
    <AppHeader />
    <p>{{ message }}</p>
    <div class="buttons">
      <button @click="onSearch">Check Purchase History</button>
      <button @click="onMenu">Menu</button>
    </div>
  </div>
</template>