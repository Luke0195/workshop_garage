CREATE TABLE tb_roles(
                         id BIGSERIAL NOT NULL PRIMARY KEY,
                         name VARCHAR(20) NOT NULL UNIQUE,
                         created_at TIMESTAMP WITHOUT TIME ZONE ,
                         updated_at TIMESTAMP WITHOUT TIME ZONE,
                         created_by TIMESTAMP WITHOUT TIME ZONE,
                         updated_by TIMESTAMP WITHOUT TIME ZONE
);

INSERT INTO tb_roles(id, name, created_at) VALUES( default, 'USER', CURRENT_TIMESTAMP);
INSERT INTO tb_roles(id, name, created_at) VALUES(default, 'ADMIN', CURRENT_TIMESTAMP);

CREATE TABLE tb_users_roles(
                               user_id BIGSERIAL NOT NULL,
                               role_id BIGSERIAL NOT NULL,
                               FOREIGN KEY (user_id) REFERENCES tb_users(id) ON DELETE SET NULL ON UPDATE CASCADE,
                               FOREIGN KEY (role_id) REFERENCES tb_roles(id) ON DELETE SET NULL ON UPDATE CASCADE
);