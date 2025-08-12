import axios from "axios";
import { useAuthStore } from "../stores/auth";

const http = axios.create({
    baseURL: import.meta.env.VITE_API_BASE
});

http.interceptors.request.use((config) => {
    const auth = useAuthStore();
    if(auth.accessToken){
        config.headers = config.headers || {};
        config.headers.Authorization = `Bearer ${auth.accessToken}`
    }
    return config;
})

http.interceptors.response.use((res) => res,
    async (error) => {
        const auth = useAuthStore();
        if(error.response?.status === 401){
            auth.logout();
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

export default http;