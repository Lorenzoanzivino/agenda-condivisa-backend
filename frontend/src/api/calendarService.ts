import apiClient from './client';
import { CalendarEvent } from '../types/Event';

export const calendarService = {
    getEvents: async (): Promise<CalendarEvent[]> => {
        const response = await apiClient.get('/api/events');
        return response.data;
    },
};