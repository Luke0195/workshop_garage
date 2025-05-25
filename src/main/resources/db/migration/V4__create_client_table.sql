CREATE TABLE tb_clients(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(75) NOT NULL,
    phone VARCHAR(25),
    email VARCHAR(150) NOT NULL UNIQUE,
    cpf VARCHAR(20) NOT NULL UNIQUE,
    zipcode VARCHAR(9),
    address TEXT,
    number SMALLINT,
    complement VARCHAR(50),
    status VARCHAR(8) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE
)
