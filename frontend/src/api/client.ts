// frontend/src/api/client.ts
import axios from 'axios';
import * as SecureStore from 'expo-secure-store';
import {Platform} from 'react-native';

const API_BASE_URL = 'https://lorenzo-agenda.loca.lt';

const apiClient = axios.create({
    baseURL: API_BASE_URL,
    headers: {'Content-Type': 'application/json'},
});

apiClient.interceptors.request.use(async (config) => {
    let token: string | null = null;

    if (Platform.OS === 'web') {
        token = localStorage.getItem('userToken');
    } else {
        token = await SecureStore.getItemAsync('userToken');
    }

    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default apiClient;