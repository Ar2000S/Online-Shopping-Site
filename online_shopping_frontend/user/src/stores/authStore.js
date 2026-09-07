import { defineStore } from "pinia";

const API_URL = '/api/auth/member-login';

export const useAuthStore = defineStore('auth', {
    state: () => ({
        memberNo: sessionStorage.getItem('memberNo') || null,
        userName: sessionStorage.getItem('userName') || null
    }),
    getters: {
        isLoggedIn: (state) => !!state.memberNo
    },
    actions: {
        async login(memberNo, password) {
            const res = await fetch(API_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',   // needed to send/receive the session cookie
                body: JSON.stringify({ memberNo, password })
            });
            if (!res.ok) {
                const err = await res.json();
                throw new Error(err.message || 'Login failed');
            }
            const data = await res.json();
            this.memberNo = data.memberNo;
            this.userName = data.userName;
            sessionStorage.setItem('memberNo', data.memberNo);
            sessionStorage.setItem('userName', data.userName);
        },
        async logout() {
            await fetch('/api/auth/logout', { method: 'POST', credentials: 'include' }).catch(() => {});
            this.memberNo = null;
            this.userName = null;
            sessionStorage.removeItem('memberNo');
            sessionStorage.removeItem('userName');
                    }
    }
});