import apiClient from './client';

export const authService = {
    login: async (email: string, password: string) => {
        try {
            const response = await apiClient.post('/api/users/login', {email, password});
            return response.data;
        } catch (error: any) {
            const message = error.response?.data?.message || "Errore durante il login";
            throw new Error(message);
        }
    },

    register: async (nome: string, email: string, password: string) => {
        try {
            const response = await apiClient.post('/api/users/register', {nome, email, password});
            return response.data;
        } catch (error: any) {
            const message = error.response?.data?.message || "Errore durante la registrazione";
            throw new Error(message);
        }
    }
};