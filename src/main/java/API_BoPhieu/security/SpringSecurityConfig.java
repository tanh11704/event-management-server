package API_BoPhieu.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Value("${app.cors.allowed-origins}")
    private String allowedOriginsString;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    authorize.requestMatchers("/api/v1/auth/**").permitAll();
                    authorize.requestMatchers("/api/v1/uploads/**").permitAll();
                    authorize.requestMatchers("/api/v1/units/**").permitAll();
                    authorize.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();
                    authorize.requestMatchers("/actuator/**").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "/api/v1/events/**").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Parse string thành list (comma-separated)
        if (allowedOriginsString != null && !allowedOriginsString.trim().isEmpty()) {
            String trimmed = allowedOriginsString.trim();
            if ("*".equals(trimmed)) {
                // Nếu là "*", dùng allowedOriginPatterns để cho phép tất cả
                config.setAllowedOriginPatterns(Arrays.asList("*"));
                config.setAllowCredentials(false);
            } else {
                // Split comma-separated string thành list và trim từng giá trị
                List<String> origins = Arrays.stream(trimmed.split(",")).map(String::trim)
                        .filter(s -> !s.isEmpty()).collect(Collectors.toList());
                config.setAllowedOrigins(origins);
                // Cho phép credentials khi có origin cụ thể (thường là trong dev)
                config.setAllowCredentials(true);
            }
        } else {
            // Default: cho phép tất cả
            config.setAllowedOriginPatterns(Arrays.asList("*"));
            config.setAllowCredentials(false);
        }

        config.setAllowedHeaders(Arrays.asList("*"));

        config.setAllowedMethods(
                Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));

        config.setExposedHeaders(Arrays.asList("*"));

        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
