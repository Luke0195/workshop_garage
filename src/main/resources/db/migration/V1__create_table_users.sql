CREATE TABLE tb_users(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(60) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by TIMESTAMP WITHOUT TIME ZONE,
    updated_by TIMESTAMP WITHOUT TIME ZONE
);