package tec.br.opticlinic.api.infra.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app.setup", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SetupDataInitializer implements ApplicationRunner {

    private final JdbcTemplate jdbc;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        // Lê configs (com valores padrão)
        String adminUser = getOrDefault("app.seed.admin.username", "admin");
        String adminPass = getOrDefault("app.seed.admin.password", "admin123"); // troque em prod
        String companyName = getOrDefault("app.seed.company.name", "Empresa");
        String companyCnpj = getOrDefault("app.seed.company.cnpj", "12.345.678/0001-99");

        ensureCompany(companyName, companyCnpj);
        ensureUser(adminUser, adminPass);
        ensureAuthority(adminUser, "ROLE_ADMIN");
        ensureAuthority(adminUser, "ROLE_USER");

        log.info("SETUP concluído (empresa/usuário/roles verificados).");
    }

    private String getOrDefault(String key, String defaultVal) {
        String v = System.getProperty(key);
        if (v == null) v = System.getenv(key.replace('.', '_').toUpperCase());
        return (v == null || v.isBlank()) ? defaultVal : v;
    }

    private void ensureCompany(String name, String cnpj) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM app_company WHERE cnpj = ?", Integer.class, cnpj);
        if (count != null && count > 0) {
            log.debug("Empresa já existe (cnpj={}).", cnpj);
            return;
        }
        jdbc.update("INSERT INTO app_company (name, cnpj) VALUES (?, ?)", name, cnpj);
        log.info("Empresa criada ({} / {}).", name, cnpj);
    }

    private void ensureUser(String username, String rawPassword) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM app_user WHERE username = ?", Integer.class, username);
        if (count != null && count > 0) {
            log.debug("Usuário já existe ({}).", username);
            return;
        }
        String encoded = ensureHasIdPrefix(passwordEncoder.encode(rawPassword));
        jdbc.update(
                "INSERT INTO app_user (username, password, enabled) VALUES (?, ?, TRUE)",
                username, encoded
        );
        log.info("Usuário criado ({}).", username);
    }

    private String ensureHasIdPrefix(String encoded) {
        // DelegatingPasswordEncoder já aplica prefixo {bcrypt}/{pbkdf2} etc.
        // Se seu encoder custom não aplicar, garanta aqui (ex.: prefixar {bcrypt}).
        return encoded.startsWith("{") ? encoded : "{bcrypt}" + encoded;
    }

    private void ensureAuthority(String username, String authority) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM app_user_authority WHERE username = ? AND authority = ?",
                Integer.class, username, authority);
        if (count != null && count > 0) {
            log.debug("Role já existe ({} -> {}).", username, authority);
            return;
        }
        jdbc.update(
                "INSERT INTO app_user_authority (username, authority) VALUES (?, ?)",
                username, authority
        );
        log.info("Role atribuída ({} -> {}).", username, authority);
    }
}
