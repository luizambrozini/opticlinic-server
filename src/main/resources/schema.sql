-- Usuários
CREATE TABLE IF NOT EXISTS app_user (
  username   VARCHAR(64) PRIMARY KEY,
  password   VARCHAR(200) NOT NULL, -- guarde o hash (ex.: BCrypt)
  enabled    BOOLEAN      NOT NULL DEFAULT TRUE
);

-- Autoridades (roles)
CREATE TABLE IF NOT EXISTS app_user_authority (
  username   VARCHAR(64) NOT NULL,
  authority  VARCHAR(64) NOT NULL,
  CONSTRAINT fk_user_auth_user FOREIGN KEY (username) REFERENCES app_user(username) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_app_user_authority_user ON app_user_authority(username);

-- Exemplo de inserção (substitua pelos hashes reais gerados no Java)
INSERT INTO app_user (username, password, enabled)
VALUES ('admin', '$2a$10$oNbzrZCtgiMveVXkW9eA4eY.uwfMyjAYEhWIud/RyCN7.u4cr23y6', true)
ON CONFLICT (username) DO NOTHING;

INSERT INTO app_user_authority (username, authority) VALUES
('admin', 'ROLE_ADMIN'),
('admin', 'ROLE_USER')
ON CONFLICT DO NOTHING;

