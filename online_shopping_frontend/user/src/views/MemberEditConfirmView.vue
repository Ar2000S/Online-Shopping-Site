<script>
import { useMemberEditStore } from '@/stores/memberEditStore.js'
import AppHeader from '@/components/AppHeader.vue'

export default {
  name: 'MemberEditConfirmView',
  components: { AppHeader },
  data() {
    return {
      edit: useMemberEditStore(),
      error: ''
    }
  },
  computed: {
    form() { return this.edit.draft }
  },
  created() {
    if (!this.form) {
      this.$router.push('/member-info/edit')
    }
  },
  methods: {
    genderLabel(g) {
      return g === 'M' ? 'Male' : g === 'F' ? 'Female' : g
    },
    async onSave() {
      this.error = ''
      try {
        await this.edit.save()
        this.$router.push('/member-info/edit/complete')
      } catch (e) {
        this.error = e.message
      }
    },
    onBack() {
      this.$router.push('/member-info/edit')
    }
  }
}
</script>

<template>
  <div v-if="form">
    <AppHeader />
    <h2>Member Information Modification Confirmation</h2>
    <p v-if="error" class="error">{{ error }}</p>

    <div class="row"><label>Name</label><span>{{ form.name }}</span></div>
    <div class="row"><label>Age</label><span>{{ form.age }}</span></div>
    <div class="row"><label>Gender</label><span>{{ genderLabel(form.gender) }}</span></div>
    <div class="row"><label>Zip Code</label><span>{{ form.zip }}</span></div>
    <div class="row"><label>Address</label><span>{{ form.address }}</span></div>
    <div class="row"><label>Phone Number</label><span>{{ form.tel }}</span></div>

    <div class="buttons">
      <button @click="onSave">Confirm</button>
      <button @click="onBack">Back</button>
    </div>
  </div>
</template>