package tec.br.opticlinic.api.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import tec.br.opticlinic.api.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/actuator/health").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(JdbcTemplate jdbc) {
        return username -> {
            // 1) Carrega dados do usuário
            record DbUser(String username, String password, boolean enabled) {}
            DbUser dbUser;
            try {
                dbUser = jdbc.queryForObject(
                        "SELECT username, password, enabled FROM app_user WHERE username = ?",
                        (rs, rn) -> new DbUser(
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getBoolean("enabled")
                        ),
                        username
                );
            } catch (org.springframework.dao.EmptyResultDataAccessException e) {
                throw new org.springframework.security.core.userdetails.UsernameNotFoundException("Usuário não encontrado: " + username);
            }

            // 2) Carrega authorities (roles)
            java.util.List<org.springframework.security.core.GrantedAuthority> auths =
                    jdbc.query(
                            "SELECT authority FROM app_user_authority WHERE username = ?",
                            (rs, rn) -> new org.springframework.security.core.authority.SimpleGrantedAuthority(rs.getString("authority")),
                            username
                    );

            // 3) Monta UserDetails (senha já deve estar codificada no banco)
            assert dbUser != null;
            return org.springframework.security.core.userdetails.User
                    .withUsername(dbUser.username())
                    .password(dbUser.password())
                    .authorities(auths)
                    .disabled(!dbUser.enabled())
                    .build();
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService uds, PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(uds);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
