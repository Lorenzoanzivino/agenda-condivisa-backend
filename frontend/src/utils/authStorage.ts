// src/utils/authStorage.ts
import * as SecureStore from 'expo-secure-store';
import { Platform } from 'react-native';

const TOKEN_KEY = 'userToken';
const USER_KEY = 'userData';

// Helper per gestire il salvataggio
export const saveAuthData = async (token: string, user: any) => {
    if (Platform.OS === 'web') {
        localStorage.setItem(TOKEN_KEY, token);
        localStorage.setItem(USER_KEY, JSON.stringify(user));
    } else {
        await SecureStore.setItemAsync(TOKEN_KEY, token);
        await SecureStore.setItemAsync(USER_KEY, JSON.stringify(user));
    }
};

// Helper per il recupero
export const getAuthData = async () => {
    if (Platform.OS === 'web') {
        const token = localStorage.getItem(TOKEN_KEY);
        const user = localStorage.getItem(USER_KEY);
        return {
            token,
            user: user ? JSON.parse(user) : null
        };
    } else {
        const token = await SecureStore.getItemAsync(TOKEN_KEY);
        const user = await SecureStore.getItemAsync(USER_KEY);
        return {
            token,
            user: user ? JSON.parse(user) : null
        };
    }
};

// Helper per la pulizia
export const clearAuthData = async () => {
    if (Platform.OS === 'web') {
        localStorage.removeItem(TOKEN_KEY);
        localStorage.removeItem(USER_KEY);
    } else {
        await SecureStore.deleteItemAsync(TOKEN_KEY);
        await SecureStore.deleteItemAsync(USER_KEY);
    }
};