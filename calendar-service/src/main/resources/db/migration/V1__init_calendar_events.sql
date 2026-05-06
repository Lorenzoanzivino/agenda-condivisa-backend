-- calendar-service/src/main/resources/db/migration/V1__init_calendar_events.sql
CREATE SCHEMA IF NOT EXISTS calendar_schema;

CREATE TABLE calendar_schema.eventi
(
    id               VARCHAR(255) PRIMARY KEY,
    titolo           VARCHAR(150) NOT NULL,
    descrizione      TEXT,
    data_inizio      TIMESTAMP    NOT NULL,
    data_fine        TIMESTAMP,
    tutto_giorno     BOOLEAN      NOT NULL DEFAULT FALSE,
    organizzatore_id VARCHAR(255) NOT NULL,
    gruppo_id        VARCHAR(255) -- Se NULL, l'evento è PRIVATO
);

CREATE TABLE calendar_schema.inviti
(
    id             VARCHAR(255) PRIMARY KEY,
    evento_id      VARCHAR(255) REFERENCES calendar_schema.eventi (id),
    utente_id      VARCHAR(255) NOT NULL,
    stato_risposta VARCHAR(50)  NOT NULL
);