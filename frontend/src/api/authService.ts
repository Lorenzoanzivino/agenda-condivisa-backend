import apiClient from './client';

export const authService = {
    login: async (email: string, password: string) => {
        try {
            const response = await apiClient.post('/api/users/login', { email, password });
            return response.data;
        } catch (error: any) {
            // Estraiamo il messaggio che arriva dal backend (Spring Boot)
            const message = error.response?.data?.message || "Errore di connessione al server";
            throw new Error(message);
        }
    },

    register: async (userData: any) => {
        return apiClient.post('/api/users/register', userData);
    }
};