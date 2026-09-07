import { defineStore } from "pinia";

export const useMessageStore = defineStore('messages', {
    state: () => ({
        messages: {},
        loaded: false
    }),
    actions: {
        async load() {
            if (this.loaded) return;
            const res = await fetch('/api/messages', { credentials: 'include' });
            if (res.ok) {
                this.messages = await res.json();
                this.loaded = true;
            }
        },
        get(code, ...args) {
            let text = this.messages[code] || code;
            // fill {0}, {1}, ... placeholders
            args.forEach((arg, i) => {
                text = text.replace(`{${i}}`, arg);
            });
            return text;
        }
    }
});