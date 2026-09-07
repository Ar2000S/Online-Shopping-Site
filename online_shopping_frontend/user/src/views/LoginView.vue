<script>
import { useAuthStore } from '@/stores/authStore.js'

export default {
  name: 'LoginView',
  data() {
    return {
      auth: useAuthStore(),
      memberNo: '',
      password: '',
      error: ''
    }
  },
  methods: {
    async doLogin() {
    this.error = ''
    try {
    await this.auth.login(this.memberNo, this.password)
    const redirect = this.$route.query.redirect
    this.$router.push(redirect ? { name: redirect } : { name: 'home' })
  } catch (e) {
    this.error = e.message
  }
    },
    clearForm() {
      this.memberNo = ''
      this.password = ''
      this.error = ''
    }
  }
}
</script>

<template>
  <div class="login-container">
    <p>Guest &nbsp; {{ new Date().toLocaleString() }}</p>

    <div class="row">
      <label>Member No:  </label>
      <input type="text" v-model="memberNo" />
    </div>
    <div class="row">
      <label>Password:  </label>
      <input type="password" v-model="password" @keyup.enter="doLogin" />
    </div>

    <div class="buttons">
      <button @click="doLogin">Login</button>
      <button @click="clearForm">Clear</button>
    </div>

    <p v-if="error" class="error">{{ error }}</p>

  </div>
</template>