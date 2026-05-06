export interface CalendarEvent {
    id: string;
    titolo: string;
    descrizione?: string;
    dataInizio: string;
    dataFine?: string;
    tuttoGiorno: boolean;    // Nuovo campo
    organizzatoreId: string;
    gruppoId?: string;       // Nuovo campo: se presente, l'evento è condiviso
}