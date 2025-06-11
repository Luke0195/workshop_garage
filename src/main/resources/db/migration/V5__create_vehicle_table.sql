CREATE TABLE tb_vehicles (
    id BIGSERIAL NOT NULL PRIMARY KEY,
     brand VARCHAR(45) NOT NULL,
     model VARCHAR(45) NOT NULL,
     vehicle_year INT NOT NULL,
     plate VARCHAR(8) NOT NULL,
     owner_id BIGSERIAL,
     created_at TIMESTAMP WITHOUT TIME ZONE,
     updated_at TIMESTAMP WITHOUT TIME ZONE,
     FOREIGN KEY (owner_id) REFERENCES tb_clients(id)
     ON DELETE SET NULL  ON UPDATE CASCADE
)
