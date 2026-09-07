<script setup>
import { RouterLink, RouterView, useRouter } from 'vue-router'
import { useAuthStore } from './stores/authStore.js';

const auth = useAuthStore()
const router = useRouter()

async function doLogout(){
  await auth.logout()
  router.push('/member-login')
  }

</script>

<template>
  <header>Le Shopping site</header>
  
<div class="wrapper">
  <nav>
    <RouterLink to="/">Home</RouterLink>
    <RouterLink to="/member-info">Account Info</RouterLink>
    <button v-if="auth.isLoggedIn" @click="doLogout" class="logout-btn">Logout</button>
  </nav>
</div>

  <RouterView />
</template>

<style scoped>
header {
  position: fixed;       
  top: 0;
  left: 0;
  width: 100%;
  height: 50px;
  line-height: 50px;     
  background-color: beige;
  text-align: center;     
  font-size: 20px;
  font-weight: bold;
  z-index: 200;          
  margin: 0;             
}

.wrapper {
  position: fixed;
  top: 50px;              
  left: 0;
  z-index: 100;
}

.main-content {          
  margin-top: 60px;
}

nav {
  display: flex;
  flex-direction: column; 
  gap: 10px;              
  padding: 15px;
  background-color: #f8f9fa; 
  border-right: 1px solid var(--color-border, #ccc);
  border-bottom: 1px solid var(--color-border, #ccc);
  font-size: 16px;
  text-align: left; 
}

nav a.router-link-exact-active {
  color: var(--color-text);
  font-weight: bold;
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: block;
  padding: 5px 0;
}

.logout-btn {
  margin-top: 10px;
  padding: 5px 10px;
  cursor: pointer;
  background-color: #f18622;
}

@media (min-width: 1024px) {
  header {
    background-color: beige;
    display: flex;
    place-items: top;

    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }


}
</style>
