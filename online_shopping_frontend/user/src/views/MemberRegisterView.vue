<script>
import { validateMem101 } from '@/utils/mem101Validation'
import { useRegistrationStore } from '@/stores/registrationStore.js'
import AppHeader from '@/components/AppHeader.vue'

const emptyForm = () => ({
  name: '', password: '', passwordConfirm: '',
  age: '', gender: '', zip: '', address: '', tel: ''
})

export default {
  name: 'MemberRegisterView',
  components: { AppHeader },
  data() {
    return {
      reg: useRegistrationStore(),
      form: emptyForm(),
      errors: {},
      message: ''
    }
  },
  methods: {
    onConfirm() {
      this.errors = validateMem101(this.form)
      if (Object.keys(this.errors).length > 0) {
        this.message = 'Please correct the highlighted fields.'
        return
      }
      this.message = ''
      this.reg.setDraft(this.form)
      this.$router.push('/member-register/confirm')
    },
    onClear() {
      this.form = emptyForm()
      this.errors = {}
      this.message = ''
    },
    onBack() {
      this.$router.push('/')
    }
  }
}
</script>

<template>
  <div class="flex-box-y">
    <AppHeader />
    <h2>Member Information Input</h2>
    <p v-if="message" class="error">{{ message }}</p>

    <div class="row">
      <label>Name: </label>
      <input type="text" v-model="form.name" />
      <span v-if="errors.name" class="error">{{ errors.name }}</span>
    </div>

    <div class="row">
      <label>Password: </label>
      <input type="password" v-model="form.password" />
      <span v-if="errors.password" class="error">{{ errors.password }}</span>
    </div>

    <div class="row">
      <label>Confirm Password : </label>
      <input type="password" v-model="form.passwordConfirm" />
      <span v-if="errors.passwordConfirm" class="error">{{ errors.passwordConfirm }}</span>
    </div>

    <div class="row">
      <label>Age: </label>
      <input type="text" v-model="form.age" />
      <span v-if="errors.age" class="error">{{ errors.age }}</span>
    </div>

    <div class="row">
      <label>Gender : </label>
      <select v-model="form.gender">
        <option value="">-- select --</option>
        <option value="M">Male</option>
        <option value="F">Female</option>
      </select>
      <span v-if="errors.gender" class="error">{{ errors.gender }}</span>
    </div>

    <div class="row">
      <label>Zip Code: </label>
      <input type="text" v-model="form.zip" placeholder="123-4567" />
      <span v-if="errors.zip" class="error">{{ errors.zip }}</span>
    </div>

    <div class="row">
      <label>Address: </label>
      <textarea v-model="form.address"></textarea>
      <span v-if="errors.address" class="error">{{ errors.address }}</span>
    </div>

    <div class="row">
      <label>Phone Number: </label>
      <input type="text" v-model="form.tel" />
      <span v-if="errors.tel" class="error">{{ errors.tel }}</span>
    </div>

    <div class="buttons flex-box-x" >
      <button @click="onConfirm">Confirm</button>
      <button @click="onBack">Back</button>
      <button @click="onClear">Clear</button>
    </div>
  </div>
</template>

<style scoped>
.flex-box-y{ display: flex; gap: 10px; padding: 20px; flex-direction: column;}
.flex-box-x{ display: flex; gap: 10px; padding: 20px; flex-direction: row;}
</style>