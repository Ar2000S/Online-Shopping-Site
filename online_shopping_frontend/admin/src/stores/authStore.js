import { defineStore } from "pinia";

const API_URL = '/api/auth/staff-login';

export const useAuthStore = defineStore('auth', {
    state: () => ({
        staffNo: sessionStorage.getItem('staffNo') || null,
        staffName: sessionStorage.getItem('staffName') || null
    }),
    getters: {
        isLoggedIn: (state) => !!state.staffNo
    },
    actions: {
        async login(staffNo, password) {
            const res = await fetch(API_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                body: JSON.stringify({ staffNo, password })
            });
            if (!res.ok) {
                const err = await res.json();
                throw new Error(err.message || 'Login failed');
            }
            const data = await res.json();
            this.staffNo = data.staffNo;
            this.staffName = data.staffName;
            sessionStorage.setItem('staffNo', data.staffNo);
            sessionStorage.setItem('staffName', data.staffName);
        },
        logout() {
            this.staffNo = null;
            this.staffName = null;
            sessionStorage.removeItem('staffNo');
            sessionStorage.removeItem('staffName');
        }
    }
});
