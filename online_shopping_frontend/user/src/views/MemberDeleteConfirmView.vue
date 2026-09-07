<script>
import { useMemberDeleteStore } from '@/stores/memberDeleteStore.js'
import { useAuthStore } from '@/stores/authStore.js'
import AppHeader from '@/components/AppHeader.vue'

export default {
  name: 'MemberDeleteConfirmView',
  components: { AppHeader },
  data() {
    return {
      del: useMemberDeleteStore(),
      auth: useAuthStore(),
      error: '',
      loaded: false
    }
  },
  computed: {
    member() { return this.del.member }
  },
  async created() {
    // Spec: "Retrieve and display the logged-in user's member information from the DB"
    const res = await fetch('/api/members/me', { credentials: 'include' })
    if (res.ok) {
      this.del.setMember(await res.json())
    }
    this.loaded = true
  },
  methods: {
    genderLabel(g) {
      return g === 'M' ? 'Male' : g === 'F' ? 'Female' : g
    },
    async onExecute() {
      this.error = ''
      try {
        await this.del.execute()
        // session was invalidated server-side (event step ②) — clear frontend auth state too
        this.auth.logout()
        this.$router.push('/member-info/delete/complete')
      } catch (e) {
        this.error = e.message
      }
    },
    onBack() {
      this.$router.push('/member-info')
    }
  }
}
</script>

<template>
  <div v-if="loaded && member">
    <AppHeader />
    <h2>Member Information Deletion Confirmation</h2>
    <p v-if="error" class="error">{{ error }}</p>

    <div class="row"><label>Member No.</label><span>{{ member.memberNo }}</span></div>
    <div class="row"><label>Name</label><span>{{ member.userName }}</span></div>
    <div class="row"><label>Age</label><span>{{ member.age }}</span></div>
    <div class="row"><label>Gender</label><span>{{ genderLabel(member.sex) }}</span></div>
    <div class="row"><label>Zip Code</label><span>{{ member.zip }}</span></div>
    <div class="row"><label>Address</label><span>{{ member.addr }}</span></div>
    <div class="row"><label>Phone Number</label><span>{{ member.tel }}</span></div>
    <div class="row"><label>Registration Date</label><span>{{ member.regDate }}</span></div>

    <div class="buttons">
      <button @click="onExecute">Execute</button>
      <button @click="onBack">Back</button>
    </div>
  </div>
</template>