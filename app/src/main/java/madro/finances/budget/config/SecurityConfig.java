package madro.finances.budget.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${app.security.username:admin}")
    private String username;

    @Value("${app.security.password:admin}")
    private String password;

    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(@Qualifier("corsConfigurationSource") CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            .authorizeHttpRequests(authz -> authz
                // Allow access to authentication endpoint
                .requestMatchers("/api/auth/login", "/api/auth/logout", "/api/auth/status").permitAll()
                // Allow access to health check endpoints
                .requestMatchers("/actuator/health", "/actuator/info", "/actuator/health/liveness", "/actuator/health/readiness").permitAll()
                // Allow access to Swagger UI and API docs
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                // Require authentication for all other endpoints
                .anyRequest().authenticated()
            )
            .httpBasic(basic -> basic.realmName("Budget API"))
            .formLogin(form -> form.disable())
            .sessionManagement(session -> session
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
            )
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.deny())
                .httpStrictTransportSecurity(hstsConfig -> hstsConfig
                    .maxAgeInSeconds(31536000)
                    .includeSubDomains(true)
                )
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
            .username(username)
            .password(passwordEncoder().encode(password))
            .roles("USER", "ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
