<script>
import { useRegistrationStore } from '@/stores/registrationStore.js'
import AppHeader from '@/components/AppHeader.vue'

export default {
  name: 'MemberCompleteView',
  components: { AppHeader },
  data() {
    return { reg: useRegistrationStore() }
  },
  computed: {
    result() { return this.reg.result }
  },
  created() {
    if (!this.result) {
      this.$router.push('/member-register')
    }
  },
  beforeUnmount() {
    this.reg.clear()   // avoids draft/result leak into a future registration attempt
  }
}
</script>

<template>
  <div v-if="result">
    <AppHeader />
    <p>{{ result.message }}</p>
    <router-link to="/">Back to Menu</router-link>
  </div>
</template>