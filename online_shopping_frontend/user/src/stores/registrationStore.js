// src/stores/registrationStore.js
import { defineStore } from "pinia";

export const useRegistrationStore = defineStore('registration', {
    state: () => ({
        draft: null,      // holds MEM101's validated form data
        result: null      // holds MEM103's { memberNo, message } kvp after successful save
    }),
    actions: {
        setDraft(form) {
            this.draft = { ...form };
        },
        async register() {
            const res = await fetch('/api/members/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                body: JSON.stringify({
                    userName: this.draft.name,
                    pwd: this.draft.password,
                    age: Number(this.draft.age),
                    sex: this.draft.gender,
                    zip: this.draft.zip,
                    addr: this.draft.address,
                    tel: this.draft.tel
                })
            });
            if (!res.ok) {
                const err = await res.json();
                throw new Error(err.message || 'Registration failed');
            }
            this.result = await res.json();
        },
        clear() {
            this.draft = null;
            this.result = null;
        }
    }
});