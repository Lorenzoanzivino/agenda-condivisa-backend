-- user-service/src/main/resources/db/migration/V1__init_users_and_groups.sql
CREATE SCHEMA IF NOT EXISTS user_schema;

CREATE TABLE user_schema.utenti
(
    id       VARCHAR(255) PRIMARY KEY,
    email    VARCHAR(255) UNIQUE NOT NULL,
    nome     VARCHAR(100)        NOT NULL,
    password VARCHAR(255)        NOT NULL
);

CREATE TABLE user_schema.groups
(
    id            VARCHAR(255) PRIMARY KEY,
    nome          VARCHAR(100)       NOT NULL,
    codice_invito VARCHAR(10) UNIQUE NOT NULL
);

CREATE TABLE user_schema.user_group_mapping
(
    id       VARCHAR(255) PRIMARY KEY,
    user_id  VARCHAR(255) REFERENCES user_schema.utenti (id),
    group_id VARCHAR(255) REFERENCES user_schema.groups (id),
    ruolo    VARCHAR(50) NOT NULL
);