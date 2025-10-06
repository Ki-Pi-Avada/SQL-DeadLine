CREATE DATABASE IF NOT EXISTS app;
USE app;

DROP TABLE IF EXISTS card_transactions;
DROP TABLE IF EXISTS auth_codes;
DROP TABLE IF EXISTS cards;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id VARCHAR(36) PRIMARY KEY,
                       login VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       status VARCHAR(50) NOT NULL DEFAULT 'active'
);

CREATE TABLE cards (
                       id VARCHAR(36) PRIMARY KEY,
                       user_id VARCHAR(36) NOT NULL,
                       number VARCHAR(19) UNIQUE NOT NULL,
                       balance_in_kopecks INT NOT NULL DEFAULT 0,
                       FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE auth_codes (
                            id VARCHAR(36) PRIMARY KEY,
                            user_id VARCHAR(36) NOT NULL,
                            code VARCHAR(6) NOT NULL,
                            created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE card_transactions (
                                   id VARCHAR(36) PRIMARY KEY,
                                   source VARCHAR(19) NOT NULL,
                                   target VARCHAR(19) NOT NULL,
                                   amount_in_kopecks INT NOT NULL,
                                   created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);