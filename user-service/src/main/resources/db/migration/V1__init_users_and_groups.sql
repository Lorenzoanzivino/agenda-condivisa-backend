CREATE TABLE users (
                       id VARCHAR(255) PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       nome VARCHAR(100),
                       cognome VARCHAR(100)
);

CREATE TABLE groups (
                        id VARCHAR(255) PRIMARY KEY,
                        nome VARCHAR(100)
);

CREATE TABLE user_group_mapping (
                                    id VARCHAR(255) PRIMARY KEY,
                                    user_id VARCHAR(255) REFERENCES users(id),
                                    group_id VARCHAR(255) REFERENCES groups(id),
                                    ruolo VARCHAR(50)
);