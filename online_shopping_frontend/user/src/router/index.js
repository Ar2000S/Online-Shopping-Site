// controls the routing

import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '@/views/LoginView.vue'
import HomeView from '@/views/HomeView.vue'
import { useAuthStore } from '@/stores/authStore.js'
import MemberRegisterView from '@/views/MemberRegisterView.vue'
import MemberConfirmView from '@/views/MemberConfirmView.vue'
import MemberCompleteView from '@/views/MemberCompleteView.vue'
import MemberInfoView from '@/views/MemberInfoView.vue'
import MemberEditView from '@/views/MemberEditView.vue'
import MemberEditConfirmView from '@/views/MemberEditConfirmView.vue'
import MemberEditCompleteView from '@/views/MemberEditCompleteView.vue'
import MemberDeleteConfirmView from '@/views/MemberDeleteConfirmView.vue'
import MemberDeleteCompleteView from '@/views/MemberDeleteCompleteView.vue'
import ProductSearchView  from '@/views/ProductSearchView.vue'
import ProductDetailsView from '@/views/ProductDetailsView.vue'
import ShoppingCartView from '@/views/ShoppingCartView.vue'
import PurchaseConfirmView from '@/views/PurchaseConfirmView.vue'
import OrderResultView from '@/views/OrderResultView.vue'
import ErrorView from '@/views/ErrorView.vue'


// in routes array:
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'home', component: HomeView},
    { path: '/member-login', name: 'member-login', component: LoginView },
    { path: '/member-register', name: 'member-register', component: MemberRegisterView },
    { path: '/member-register/confirm', name: 'member-register-confirm', component: MemberConfirmView },
    { path: '/member-register/complete', name: 'member-register-complete', component: MemberCompleteView },
    { path: '/member-info', name: 'member-info', component: MemberInfoView, meta: { requiresAuth: true } },
    { path: '/member-info/edit', name: 'member-edit', component: MemberEditView, meta: { requiresAuth: true } },
    { path: '/member-info/edit/confirm', name: 'member-edit-confirm', component: MemberEditConfirmView, meta: { requiresAuth: true } },
    { path: '/member-info/edit/complete', name: 'member-edit-complete', component: MemberEditCompleteView, meta: { requiresAuth: true } },
    { path: '/member-info/delete-confirm', name: 'member-delete-confirm', component: MemberDeleteConfirmView, meta: { requiresAuth: true } },
    { path: '/member-info/delete/complete', name: 'member-delete-complete', component: MemberDeleteCompleteView },
    { path: '/product-search', name: 'product-search', component: ProductSearchView },
    { path: '/product-search/:code', name: 'product-details', component: ProductDetailsView },
    { path: '/cart', name: 'cart', component: ShoppingCartView },
    { path: '/cart/confirm', name: 'cart-confirm', component: PurchaseConfirmView },
    { path: '/cart/complete', name: 'cart-complete', component: OrderResultView },
    { path: '/error', name: 'error', component: ErrorView },
    { path: '/:pathMatch(.*)*', name: 'not-found', component: ErrorView },  // This NEEDS to be the last element of this list. Will match urls
    
  ]
})

router.beforeEach((to) => {
  const auth = useAuthStore()

  // protect authenticated-only routes
  if (to.meta.requiresAuth && !auth.isLoggedIn) {
    return { name: 'member-login' }
  }

  // if already logged in, don't allow going back to the login page
  if (to.name === 'member-login' && auth.isLoggedIn) {
    return { name: 'home' }
  }
})

export default router