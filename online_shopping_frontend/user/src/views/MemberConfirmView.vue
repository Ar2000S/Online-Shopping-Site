<script>
import { useRegistrationStore } from '@/stores/registrationStore.js'
import AppHeader from '@/components/AppHeader.vue'

export default {
  name: 'MemberConfirmView',
  components: { AppHeader },
  data() {
    return {
      reg: useRegistrationStore(),
      error: ''
    }
  },
  computed: {
    form() { return this.reg.draft }
  },
  created() {
    // MEM102 has nothing to show if reached directly without going through MEM101 first
    if (!this.form) {
      this.$router.push('/member-register')
    }
  },
  methods: {
    genderLabel(g) {
      return g === 'M' ? 'Male' : g === 'F' ? 'Female' : g
    },
    async onRegister() {
      this.error = ''
      try {
        await this.reg.register()
        this.$router.push('/member-register/complete')   // MEM103
      } catch (e) {
        this.error = e.message
      }
    },
    onBack() {
      this.$router.push('/member-register')   // MEM101, form data still in this.form for re-display
    }
  }
}
</script>

<template>
  <div v-if="form">
    <AppHeader />
    <h2>Member Information Confirmation</h2>
    <p v-if="error" class="error">{{ error }}</p>

    <div class="row"><label>Name</label><span>{{ form.name }}</span></div>
    <div class="row"><label>Age</label><span>{{ form.age }}</span></div>
    <div class="row"><label>Gender</label><span>{{ genderLabel(form.gender) }}</span></div>
    <div class="row"><label>Zip Code</label><span>{{ form.zip }}</span></div>
    <div class="row"><label>Address</label><span>{{ form.address }}</span></div>
    <div class="row"><label>Phone Number</label><span>{{ form.tel }}</span></div>

    <div class="buttons">
      <button @click="onRegister">Register</button>
      <button @click="onBack">Back</button>
    </div>
  </div>
</template>