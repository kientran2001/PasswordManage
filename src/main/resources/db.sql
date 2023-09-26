
--CREATE TABLE users
--(
--    username varchar(50) PRIMARY KEY,
--    password varchar(500) NOT NULL,
--    enabled BOOLEAN NOT NULL
--);

CREATE TABLE users
(
    id SERIAL PRIMARY KEY,
    username varchar(50) UNIQUE NOT NULL,
    salt varchar(50) NOT NULL,
    hmac varchar(500) NOT NULL
);

--INSERT INTO users (id, username, password, role)
--VALUES(1, 'kien', 'kien', 'admin')

--CREATE TABLE authorities
--(
--    id SERIAL PRIMARY KEY,
--    username varchar(50) NOT NULL,
--    authority varchar(50) NOT NULL,
--    CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE
--);

CREATE TABLE accounts
(
    id SERIAL PRIMARY KEY,
    userId SERIAL,
    acc varchar(50) NOT NULL,
    pass varchar(500) NOT NULL,
    web varchar(500) NOT NULL,
    CONSTRAINT fk_uId FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE
);
