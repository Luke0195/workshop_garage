CREATE TABLE tb_password_reset_tokens(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    user_id BIGSERIAL,
    token UUID NOT NULL,
    expires_at TIMESTAMP WITHOUT TIME ZONE,
    used boolean,
    FOREIGN KEY(user_id) REFERENCES tb_users(id) ON UPDATE CASCADE ON DELETE SET NULL

);