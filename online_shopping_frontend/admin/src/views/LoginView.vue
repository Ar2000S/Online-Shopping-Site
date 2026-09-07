<script>
import { useAuthStore } from '@/stores/authStore.js'

export default {
  name: 'LoginView',
  data() {
    return {
      auth: useAuthStore(),
      staffNo: '',
      password: '',
      error: ''
    }
  },
  methods: {
    async doLogin() {
      this.error = ''
      try {
        await this.auth.login(this.staffNo, this.password)
        this.$router.push('/')
      } catch (e) {
        this.error = e.message
      }
    },
    clearForm() {
      this.staffNo = ''
      this.password = ''
      this.error = ''
    }
  }
}
</script>

<template>
  <div class="login-container">
    <div class="row">
      <label>Staff No.</label>
      <input type="text" v-model="staffNo" />
    </div>
    <div class="row">
      <label>Password</label>
      <input type="password" v-model="password" @keyup.enter="doLogin" />
    </div>
    <div class="buttons">
      <button @click="doLogin">Login</button>
      <button @click="clearForm">Clear</button>
    </div>
    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>