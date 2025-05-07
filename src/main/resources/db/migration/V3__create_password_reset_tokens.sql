CREATE TABLE tb_password_reset_tokens(
    id UUID NOT NULL PRIMARY KEY,
    user_id UUID,
    token UUID NOT NULL,
    expires_at TIMESTAMP WITHOUT TIME ZONE,
    used boolean,
    FOREIGN KEY(user_id) REFERENCES tb_users(id) ON UPDATE CASCADE ON DELETE SET NULL

);