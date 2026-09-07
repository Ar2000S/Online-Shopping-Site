<script>
import AppHeader from '@/components/AppHeader.vue'

export default {
  name: 'MemberInfoView',
  components: { AppHeader },
  data() {
    return {
      member: null,
      error: ''
    }
  },
  async created() {
    try {
      const res = await fetch('/api/members/me', {
        credentials: 'include'
      })
      if (!res.ok) {
        const err = await res.json()
        throw new Error(err.message || 'Failed to load member information')
      }
      this.member = await res.json()
    } catch (e) {
      this.error = e.message
    }
  },
  methods: {
    genderLabel(g) {
      return g === 'M' ? 'Male' : g === 'F' ? 'Female' : g
    },
    onEdit() {
      this.$router.push('/member-info/edit')
    },
    onDelete() {
      this.$router.push('/member-info/delete-confirm')
    },
    onMenu() {
      this.$router.push('/')
    }
  }
}
</script>

<template>
  <div>
    <AppHeader />
    <h2>Member Information</h2>

    <p v-if="error" class="error">{{ error }}</p>

    <div v-if="member">
      <div class="row"><label>Member No. : </label><span>{{ member.memberNo }}</span></div>
      <div class="row"><label>Name: </label><span>{{ member.userName }}</span></div>
      <div class="row"><label>Age: </label><span>{{ member.age }}</span></div>
      <div class="row"><label>Gender: </label><span>{{ genderLabel(member.sex) }}</span></div>
      <div class="row"><label>Zip Code: </label><span>{{ member.zip }}</span></div>
      <div class="row"><label>Address: </label><span>{{ member.addr }}</span></div>
      <div class="row"><label>Phone Number: </label><span>{{ member.tel }}</span></div>
      <div class="row"><label>Registration Date: </label><span>{{ member.regDate }}</span></div>

      <div class="buttons">
        <button @click="onEdit">Modify</button>
        <button @click="onDelete">Delete</button>
        <button @click="onMenu">To Menu</button>
      </div>
    </div>
  </div>
</template>