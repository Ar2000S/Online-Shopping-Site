<script setup>
import { RouterLink, RouterView, useRouter } from 'vue-router'
import { useAuthStore } from './stores/authStore.js'

const auth = useAuthStore()
const router = useRouter()

function doLogout(){
  auth.logout()
  router.push('/staff-login')
}

</script>

<template>
  <header class="top-bar">
    <RouterLink class="title" to="/">Admin Panel</RouterLink>
    <div class="top-actions">
      <button v-if="auth.isLoggedIn" @click="doLogout" class="logout-btn">Logout</button>
    </div>
  </header>

  <main class="content">
    <RouterView />
  </main>
</template>


<style scoped>
.top-bar {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 50px;
  background-color: #f1f5f9;     /* light color */
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
  display: flex;
  align-items: center;
  justify-content: space-between;  /* title left, actions right */
  padding: 0 20px;
  box-sizing: border-box;
  z-index: 100;
}

.left-bar {
  position: fixed;
  left: 0;
  top: 50px;              
  width: 200px;           
  height: calc(100vh - 50px);  /* full remaining height below, relative to the top bar. so using vh */
  padding: 20px;
  box-sizing: border-box; /* so padding doesnt overflow the width */
  background-color: rgba(142, 247, 93, 0.3);
  border-right: 2px solid #6ad5f0;
  overflow-y: auto;       /* scroll if huge */
}

.title {
  font-size: 18px;
  font-weight: bold;
  color: #334155;
}

.top-actions {
  display: flex;
  align-items: center;
  gap: 12px;
}

.content {
  padding-top: 60px;
  padding-left: 20px;
  padding-right: 20px;
  min-height: 75vh;
  background: linear-gradient(-45deg, #f8f660, #ccf0a2, #c6cdf1, #f1a8c1, #fa6f9f);
  background-size: 400% 400%;
  animation: gentleShift 10s ease infinite;
}

@keyframes gentleShift {
  0%   { background-position: 0% 50%; }
  50%  { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}
</style>