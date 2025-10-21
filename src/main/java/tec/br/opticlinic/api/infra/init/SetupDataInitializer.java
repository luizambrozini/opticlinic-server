package tec.br.opticlinic.api.infra.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tec.br.opticlinic.api.infra.model.Company;
import tec.br.opticlinic.api.infra.model.User;
import tec.br.opticlinic.api.infra.model.UserAuthority;
import tec.br.opticlinic.api.infra.repository.CompanyRepository;
import tec.br.opticlinic.api.infra.repository.UserAuthorityRepository;
import tec.br.opticlinic.api.infra.repository.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app.setup", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SetupDataInitializer implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final UserAuthorityRepository userAuthorityRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        String adminUser   = getOrDefault("app.seed.admin.username", "admin");
        String adminPass   = getOrDefault("app.seed.admin.password", "admin123"); // troque em prod
        String companyName = getOrDefault("app.seed.company.name", "Empresa");
        String companyCnpj = getOrDefault("app.seed.company.cnpj", "12.345.678/0001-99");

        Company company = ensureCompany(companyName, companyCnpj);
        User user = ensureUser(adminUser, adminPass, company);
        ensureAuthority(user, "ROLE_ADMIN");
        ensureAuthority(user, "ROLE_USER");

        log.info("SETUP concluído (empresa/usuário/roles verificados).");
    }

    private String getOrDefault(String key, String defaultVal) {
        String v = System.getProperty(key);
        if (v == null) v = System.getenv(key.replace('.', '_').toUpperCase());
        return (v == null || v.isBlank()) ? defaultVal : v;
    }

    private Company ensureCompany(String name, String cnpj) {
        String cnpjNum = cnpj.replaceAll("\\D", ""); // mantenha só dígitos, se sua coluna for 14 chars
        return companyRepository.findByCnpj(cnpjNum).orElseGet(() -> {
            Company newCompany = new Company();
            newCompany.setId(1L);
            newCompany.setName(name);
            newCompany.setCnpj(cnpjNum);
            Company saved = companyRepository.save(newCompany); // persist
            log.info("Empresa criada ({} / {}).", name, cnpj);
            return saved;
        });
    }

    private User ensureUser(String username, String rawPassword, Company company) {
        return userRepository.findByUsername(username).orElseGet(() -> {
            String encoded = passwordEncoder.encode(rawPassword);
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(encoded);
            newUser.setEnabled(true);
            newUser.setCompany(company); // exige @ManyToOne na entidade User
            User saved = userRepository.save(newUser);
            log.info("Usuário criado ({}).", username);
            return saved;
        });
    }

    private void ensureAuthority(User user, String authority) {
        // OPÇÃO A (recomendada): método derivado por user_id
        boolean exists = userAuthorityRepository.existsByUserAndAuthority(user, authority);
        if (exists) {
            log.debug("Role já existe ({} -> {}).", user.getUsername(), authority);
            return;
        }
        UserAuthority ua = new UserAuthority();
        ua.setUser(user);
        ua.setAuthority(authority);
        userAuthorityRepository.save(ua);
        log.info("Role atribuída ({} -> {}).", user.getUsername(), authority);


    }
}
