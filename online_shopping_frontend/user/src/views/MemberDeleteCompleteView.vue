<script>
import { useMemberDeleteStore } from '@/stores/memberDeleteStore.js'
import AppHeader from '@/components/AppHeader.vue'

export default {
  name: 'MemberDeleteCompleteView',
  components: { AppHeader },
  data() {
    return { del: useMemberDeleteStore() }
  },
  computed: {
    result() { return this.del.result }
  },
  created() {
    if (!this.result) {
      this.$router.push('/')
    }
  },
  beforeUnmount() {
    this.del.clear()
  }
}
</script>

<template>
  <div v-if="result">
    <AppHeader />
    <p>{{ result.message }}</p>
    <router-link to="/">Go to Menu</router-link>
  </div>
</template>