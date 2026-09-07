<script>
import AppHeader from '@/components/AppHeader.vue'

export default {
  name: 'ErrorView',
  components: { AppHeader },
  data() {
    return {
      message: 'An unexpected error has occurred. \n Are you sure this is the right page? \n Please contact customer service, if the issue persists.'
    }
  },
  methods: {
    onMenu() {
      this.$router.push('/')   // MEN101
    }
  }
}
</script>

<template>
  <div>
    <AppHeader />
    <p class="error">{{ message }}</p>
    <button @click="onMenu">Back to Menu</button>
  </div>
</template>