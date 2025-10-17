-- USUÁRIOS
CREATE TABLE IF NOT EXISTS app_user (
  id         SERIAL PRIMARY KEY,
  username   VARCHAR(64) NOT NULL,
  password   VARCHAR(200) NOT NULL,
  enabled    BOOLEAN      NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Garante unicidade do username (idempotente, sem DO $$)
CREATE UNIQUE INDEX IF NOT EXISTS idx_app_user_username ON app_user(username);

-- AUTORIDADES
CREATE TABLE IF NOT EXISTS app_user_authority (
  id        SERIAL PRIMARY KEY,
  username  VARCHAR(64) NOT NULL,
  authority VARCHAR(64) NOT NULL,
  CONSTRAINT fk_user_auth_user
    FOREIGN KEY (username) REFERENCES app_user(username) ON DELETE CASCADE
);

-- Evita duplicatas de (username, authority) – substitui a UNIQUE por índice único
CREATE UNIQUE INDEX IF NOT EXISTS uq_app_user_authority ON app_user_authority(username, authority);

-- EMPRESAS
CREATE TABLE IF NOT EXISTS app_company (
  id         SERIAL PRIMARY KEY,
  name       VARCHAR(100) NOT NULL,
  cnpj       VARCHAR(20)  NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- CNPJ único (idempotente)
CREATE UNIQUE INDEX IF NOT EXISTS idx_app_company_cnpj ON app_company(cnpj);
