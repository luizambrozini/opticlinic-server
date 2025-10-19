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
import tec.br.opticlinic.api.infra.dao.CompanyDao;
import tec.br.opticlinic.api.infra.dao.UserDao;
import tec.br.opticlinic.api.infra.model.Company;
import tec.br.opticlinic.api.infra.model.User;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app.setup", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SetupDataInitializer implements ApplicationRunner {

    private final JdbcTemplate jdbc;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final CompanyDao companyDao;

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
        var companyOpt = companyDao.findById(1L);
        if (companyOpt.isPresent()) {
            log.debug("Empresa já existe ({} / {}).", name, cnpj);
            return;
        }
        var newCompany = new Company();
        newCompany.setName(name);
        newCompany.setCnpj(cnpj);
        companyDao.insert(newCompany);
        log.info("Empresa criada ({} / {}).", name, cnpj);
    }

    private void ensureUser(String username, String rawPassword) {
        var userOpt = userDao.findByUsername(username);
        if (userOpt.isPresent()) {
            log.debug("Usuário já existe ({}).", username);
            return;
        }
        String encodedPassword = passwordEncoder.encode(rawPassword);
        var newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encodedPassword);
        newUser.setEnabled(true);
        userDao.insert(newUser);
        log.info("Usuário criado ({}).", username);
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
