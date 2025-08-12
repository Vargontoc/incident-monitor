import http from "./http";

export async function login(username: string, password: string) {
    const { data } = await http.post('/auth/login', { username, password });
    return data as { accessToken: string, refreshToken: string };
}