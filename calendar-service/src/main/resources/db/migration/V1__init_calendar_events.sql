CREATE TABLE eventi
(
    id               VARCHAR(255) PRIMARY KEY,
    titolo           VARCHAR(150) NOT NULL,
    descrizione      TEXT,
    data_inizio      TIMESTAMP    NOT NULL,
    data_fine        TIMESTAMP    NOT NULL,
    organizzatore_id VARCHAR(255) NOT NULL
);

CREATE TABLE inviti
(
    id             VARCHAR(255) PRIMARY KEY,
    evento_id      VARCHAR(255) REFERENCES eventi (id),
    utente_id      VARCHAR(255) NOT NULL,
    stato_risposta VARCHAR(50)  NOT NULL
);