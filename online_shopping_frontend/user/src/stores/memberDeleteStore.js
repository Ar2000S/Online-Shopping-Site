import { defineStore } from "pinia";

export const useMemberDeleteStore = defineStore('memberDelete', {
    state: () => ({
        member: null,    // holds MEM201's data for display on MEM301
        result: null     // holds MEM302's result message
    }),
    actions: {
        setMember(member) {
            this.member = { ...member };
        },
        async execute() {
            const res = await fetch('/api/members/me', {
                method: 'DELETE',
                credentials: 'include'
            });
            if (!res.ok) {
                const err = await res.json();
                throw new Error(err.message || 'Deletion failed');
            }
            this.result = await res.json();
        },
        clear() {
            this.member = null;
            this.result = null;
        }
    }
});