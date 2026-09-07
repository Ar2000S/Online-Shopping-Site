<script>
import AppHeader from '@/components/AppHeader.vue'

export default {
  name: 'OrderResultView',
  components: { AppHeader },
  data() {
    return { message: 'Your order has been completed.' }
  },
  methods: {
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
    <button @click="onMenu">To Menu</button>
  </div>
</template>