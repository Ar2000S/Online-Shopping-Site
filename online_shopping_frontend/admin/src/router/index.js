import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/authStore.js'

import LoginView from '@/views/LoginView.vue'
import HomeView from '@/views/HomeView.vue'
import HistorySearchView from '@/views/HistorySearchView.vue'
import HistoryDetailsView from '@/views/HistoryDetailsView.vue'
import HistoryDeleteConfirmView from '@/views/HistoryDeleteConfirmView.vue'
import HistoryDeleteResultView from '@/views/HistoryDeleteResultView.vue'
import ErrorView from '@/views/ErrorView.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/staff-login', name: 'staff-login', component: LoginView },
    { path: '/', name: 'home', component: HomeView, meta: { requiresAuth: true } },
    { path: '/history', name: 'history', component: HistorySearchView, meta: { requiresAuth: true } },
    { path: '/history/:collectNo', name: 'history-details', component: HistoryDetailsView, meta: { requiresAuth: true } },
    { path: '/history/:collectNo/delete', name: 'history-delete-confirm', component: HistoryDeleteConfirmView, meta: { requiresAuth: true } },
    { path: '/history/deleted', name: 'history-deleted', component: HistoryDeleteResultView, meta: { requiresAuth: true } },
    { path: '/error', name: 'error', component: ErrorView },
    { path: '/:pathMatch(.*)*', name: 'not-found', component: ErrorView },
      
  ]
})

router.beforeEach((to) => {
  const auth = useAuthStore()
  if (to.meta.requiresAuth && !auth.isLoggedIn) {
    return { name: 'staff-login' }
  }
  if (to.name === 'staff-login' && auth.isLoggedIn) {
    return { name: 'home' }
  }
})

export default router