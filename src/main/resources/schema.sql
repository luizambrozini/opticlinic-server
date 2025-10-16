-- Usuários
CREATE TABLE IF NOT EXISTS app_user (
    id SERIAL PRIMARY KEY,
    username   VARCHAR(64) PRIMARY KEY,
    password   VARCHAR(200) NOT NULL, -- guarde o hash (ex.: BCrypt)
    enabled    BOOLEAN      NOT NULL DEFAULT TRUE
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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

CREATE TABLE IF NOT EXISTS app_company (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cnpj VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_app_company_cnpj ON app_company(cnpj);

INSERT INTO app_company (id, name, cnpj) VALUES
(1, 'Empresa', '12.345.678/0001-99')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS app_people (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cpf VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    company_id INT,
    CONSTRAINT fk_employee_company FOREIGN KEY (company_id) REFERENCES app_company(id) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_app_people_company ON app_people(company_id);