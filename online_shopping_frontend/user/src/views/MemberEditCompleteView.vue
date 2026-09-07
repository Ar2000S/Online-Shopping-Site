<script>
import { useMemberEditStore } from '@/stores/memberEditStore.js'
import AppHeader from '@/components/AppHeader.vue'

export default {
  name: 'MemberEditCompleteView',
  components: { AppHeader },
  data() {
    return { edit: useMemberEditStore() }
  },
  computed: {
    result() { return this.edit.result }
  },
  created() {
    if (!this.result) {
      this.$router.push('/member-info')
    }
  },
  beforeUnmount() {
    this.edit.clear()
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