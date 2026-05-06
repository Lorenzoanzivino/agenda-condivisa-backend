import apiClient from './client';
import {CalendarEvent} from '../types/Event';

export const calendarService = {
    // Carica eventi personali (swap a sinistra)
    getPersonalEvents: async (): Promise<CalendarEvent[]> => {
        const response = await apiClient.get('/api/events/me');
        return response.data;
    },

    // Carica eventi di un gruppo specifico (swap a destra)
    getGroupEvents: async (groupId: string): Promise<CalendarEvent[]> => {
        const response = await apiClient.get(`/api/events/group/${groupId}`);
        return response.data;
    },

    createEvent: async (eventData: Partial<CalendarEvent>) => {
        const response = await apiClient.post('/api/events', eventData);
        return response.data;
    },

    getUserGroups: async (userId: string) => {
        const response = await apiClient.get(`/api/memberships/users/${userId}/groups`);
        return response.data;
    },

    // Crea un nuovo gruppo
    createGroup: async (nome: string) => {
        const response = await apiClient.post('/api/groups', {nome});
        return response.data;
    },

    // Si unisce a un gruppo esistente via codice
    joinGroup: async (codiceInvito: string) => {
        const response = await apiClient.post('/api/groups/join', {codiceInvito});
        return response.data;
    }
};