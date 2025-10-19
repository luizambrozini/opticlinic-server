package tec.br.opticlinic.api.config;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

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

        record DbUser(Long id,String username, String password, boolean enabled) {}

        RowMapper<DbUser> userRowMapper = (rs, rn) ->
                new DbUser(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getBoolean("enabled")
                );

        RowMapper<GrantedAuthority> authRowMapper = (rs, rn) ->
                new SimpleGrantedAuthority(rs.getString("authority"));

        return username -> {
            // 1) Usuário
            DbUser dbUser;
            try {
                dbUser = jdbc.queryForObject(
                        "SELECT id, username, password, enabled FROM app_user WHERE username = ?",
                        userRowMapper,
                        username
                );
            } catch (EmptyResultDataAccessException e) {
                throw new UsernameNotFoundException("Usuário não encontrado: " + username);
            }

            // 2) Authorities (roles)
            List<GrantedAuthority> authsList = jdbc.query(
                    "SELECT DISTINCT authority FROM app_user_authority WHERE username = ? ORDER BY authority",
                    authRowMapper,
                    username
            );

            // opcional: transformar em Set imutável
            Collection<GrantedAuthority> authorities = Collections.unmodifiableSet(new HashSet<>(authsList));

            // 3) Monta o UserDetails
            return User.withUsername(dbUser.username())
                    .password(dbUser.password()) // já deve vir com {bcrypt}... se usar DelegatingPasswordEncoder
                    .authorities(authorities)
                    .disabled(!dbUser.enabled())
                    // .accountLocked(false).accountExpired(false).credentialsExpired(false) // caso queira explicitar
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
