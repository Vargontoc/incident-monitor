import { defineStore } from "pinia";

type Tokens = { accessToken: string; refreshToken?: string }

export const useAuthStore = defineStore('auth', {
    state: () => ({
        accessToken: localStorage.getItem('accessToken') || '',
    }),
    getters: {
        isLoggedIn: (s) => !!s.accessToken
    },
    actions: {
        setTokens(t: Tokens) {
            this.accessToken = t.accessToken;
            localStorage.setItem('accessToken', t.accessToken)
        },
        logout() {
            this.accessToken = '';
            localStorage.removeItem('accessToken')
        }
    }
})