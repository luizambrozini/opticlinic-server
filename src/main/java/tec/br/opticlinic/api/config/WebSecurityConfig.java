package tec.br.opticlinic.api.config;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tec.br.opticlinic.api.security.AuthEntryPointJwt;
import tec.br.opticlinic.api.security.AuthTokenFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

        @Autowired
        private AuthEntryPointJwt unauthorizedHandler;

        @Autowired
        private AuthTokenFilter authTokenFilter;

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
                return cfg.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // Chain 1: APIs com JWT
        @Bean
        @Order(1)
        public SecurityFilterChain apiChain(HttpSecurity http) throws Exception {
                http
                                .securityMatcher("/api/**")
                                .csrf(AbstractHttpConfigurer::disable)
                                .cors(AbstractHttpConfigurer::disable)
                                .exceptionHandling(e -> e.authenticationEntryPoint(unauthorizedHandler))
                                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/auth/**", "/error/**").permitAll()
                                                .anyRequest().authenticated())
                                .addFilterBefore(
                                                authTokenFilter,
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        // Chain 2: Páginas JSP
        @Bean
        @Order(2)
        public SecurityFilterChain webChain(HttpSecurity http) throws Exception {
                http
                                // Habilite CSRF no fluxo web (remova o disable para a web)
                                .csrf(AbstractHttpConfigurer::disable) // <-- remova isso na web, deixe CSRF ativo
                                .cors(AbstractHttpConfigurer::disable)
                                // Sessão necessária para login form
                                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                                .authorizeHttpRequests(auth -> auth
                                                // necessário para JSP sob /WEB-INF/**
                                                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.INCLUDE)
                                                .permitAll()

                                                // públicos (login + estáticos)
                                                .requestMatchers(
                                                                "/app/auth/**",
                                                                "/favicon.ico", "/.well-known/**",
                                                                "/css/**", "/js/**", "/images/**", "/webjars/**")
                                                .permitAll()

                                                // protege todo /app/**
                                                .requestMatchers("/app/**").authenticated()

                                                // outras rotas web (se existirem) — ajuste conforme seu caso
                                                .requestMatchers("/", "/WEB-INF/**").permitAll()

                                                .anyRequest().permitAll())
                                .formLogin(form -> form
                                                .loginPage("/app/auth/login") // GET: sua página JSP
                                                .loginProcessingUrl("/app/auth/login") // POST: processamento do login
                                                .defaultSuccessUrl("/app", true) // para onde vai depois de logar
                                                .failureUrl("/app/auth/login?error") // feedback de erro
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/app/auth/logout") // URL de logout
                                                .logoutSuccessUrl("/app/auth/login?logout") // para onde redireciona
                                                                                            // após logout
                                                .invalidateHttpSession(true) // invalida a sessão
                                                .deleteCookies("JSESSIONID") // remove cookie da sessão
                                                .permitAll());

                return http.build();
        }
}
