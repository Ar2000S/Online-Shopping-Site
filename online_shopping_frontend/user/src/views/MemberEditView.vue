<script>
import { validateMem101 } from '@/utils/mem101Validation'
import { useMemberEditStore } from '@/stores/memberEditStore.js'
import AppHeader from '@/components/AppHeader.vue'

export default {
  name: 'MemberEditView',
  components: { AppHeader },
  data() {
    return {
      edit: useMemberEditStore(),
      form: { name: '', password: '', passwordConfirm: '', age: '', gender: '', zip: '', address: '', tel: '' },
      errors: {},
      message: '',
      loaded: false
    }
  },
  async created() {
    // pre-fill from the existing draft (e.g., returning from MEM203 "Back"), or fetch fresh
    if (this.edit.draft) {
      this.form = { ...this.edit.draft }
      this.loaded = true
      return
    }
    const res = await fetch('/api/members/me', { credentials: 'include' })
    if (res.ok) {
      const data = await res.json()
      this.form = {
        name: data.userName,
        password: '',            // never pre-fill password
        passwordConfirm: '',
        age: String(data.age),
        gender: data.sex,
        zip: data.zip,
        address: data.addr,
        tel: data.tel
      }
    }
    this.loaded = true
  },
  methods: {
    onConfirm() {
      // password is optional on edit — only validate it if the user typed something
      const toValidate = { ...this.form }
      const skippingPassword = !this.form.password && !this.form.passwordConfirm
      if (skippingPassword) {
        toValidate.password = 'placeholder'
        toValidate.passwordConfirm = 'placeholder'
      }
      this.errors = validateMem101(toValidate)
      if (skippingPassword) {
        delete this.errors.password
        delete this.errors.passwordConfirm
      }
      if (Object.keys(this.errors).length > 0) {
        this.message = 'Please correct the highlighted fields.'
        return
      }
      this.message = ''
      this.edit.setDraft(this.form)
      this.$router.push('/member-info/edit/confirm')
    },
    onClear() {
      this.form.name = ''
      this.form.password = ''
      this.form.passwordConfirm = ''
      this.form.age = ''
      this.form.gender = ''
      this.form.zip = ''
      this.form.address = ''
      this.form.tel = ''
      this.errors = {}
      this.message = ''
    },
    onBack() {
      this.$router.push('/member-info')
    }
  }
}
</script>

<template>
  <div v-if="loaded">
    <AppHeader />
    <h2>Edit Member Information</h2>
    <p v-if="message" class="error">{{ message }}</p>
    <p class="hint">Leave password fields blank to keep your current password.</p>

    <div class="row">
      <label>Name</label>
      <input type="text" v-model="form.name" />
      <span v-if="errors.name" class="error">{{ errors.name }}</span>
    </div>

    <div class="row">
      <label>Password</label>
      <input type="password" v-model="form.password" />
      <span v-if="errors.password" class="error">{{ errors.password }}</span>
    </div>

    <div class="row">
      <label>Password (Confirmation)</label>
      <input type="password" v-model="form.passwordConfirm" />
      <span v-if="errors.passwordConfirm" class="error">{{ errors.passwordConfirm }}</span>
    </div>

    <div class="row">
      <label>Age</label>
      <input type="text" v-model="form.age" />
      <span v-if="errors.age" class="error">{{ errors.age }}</span>
    </div>

    <div class="row">
      <label>Gender</label>
      <select v-model="form.gender">
        <option value="M">Male</option>
        <option value="F">Female</option>
      </select>
      <span v-if="errors.gender" class="error">{{ errors.gender }}</span>
    </div>

    <div class="row">
      <label>Zip Code</label>
      <input type="text" v-model="form.zip" />
      <span v-if="errors.zip" class="error">{{ errors.zip }}</span>
    </div>

    <div class="row">
      <label>Address</label>
      <textarea v-model="form.address"></textarea>
      <span v-if="errors.address" class="error">{{ errors.address }}</span>
    </div>

    <div class="row">
      <label>Phone Number</label>
      <input type="text" v-model="form.tel" />
      <span v-if="errors.tel" class="error">{{ errors.tel }}</span>
    </div>

    <div class="buttons">
      <button @click="onConfirm">Confirm</button>
      <button @click="onClear">Clear</button>
      <button @click="onBack">Back</button>
    </div>
  </div>
</template>