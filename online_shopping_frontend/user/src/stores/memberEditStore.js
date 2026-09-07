import { defineStore } from "pinia";

export const useMemberEditStore = defineStore('memberEdit', {
    state: () => ({
        draft: null,     // holds MEM202's form data, carried to MEM203
        result: null     // holds MEM204's result message
    }),
    actions: {
        setDraft(form) {
            this.draft = { ...form };
        },
        async save() {
            const res = await fetch('/api/members/me', {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                body: JSON.stringify({
                    userName: this.draft.name,
                    pwd: this.draft.password || null,
                    age: Number(this.draft.age),
                    sex: this.draft.gender,
                    zip: this.draft.zip,
                    addr: this.draft.address,
                    tel: this.draft.tel
                })
            });
            if (!res.ok) {
                const err = await res.json();
                throw new Error(err.message || 'Update failed');
            }
            this.result = await res.json();
        },
        clear() {
            this.draft = null;
            this.result = null;
        }
    }
});