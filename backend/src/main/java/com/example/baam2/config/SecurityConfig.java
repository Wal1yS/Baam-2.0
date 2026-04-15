package com.example.baam2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf
                .csrfTokenRepository(csrfTokenRepository())
                .ignoringRequestMatchers("/user/login", "/user/register"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/login", "/user/register" ).permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers
                    .contentSecurityPolicy(csp -> csp.policyDirectives(
                        // basic scp policy
                        /** (Джарвис комменты, что бы не забыть что для чего)
                         * deafault-src 'self' - разрешает загружать ресурсы только с того же домена, что и приложение
                         * frame-ancestors 'none' - запрещает встраивать приложение в iframe
                         * base-uri 'self' - разрешает использовать только URL-адреса из того же домена для базовых URI
                         * object-src 'none' - запрещает загрузку плагинов и других объектов
                        **/
                        "default-src 'self'; frame-ancestors 'none'; base-uri 'self'; object-src 'none'"
                    ))
                    .frameOptions(frame -> frame.deny())
                    .xssProtection(xss -> xss.disable())
                )
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("http://localhost:5173")); // in future should be some domen (i guess) 
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "X-CSRF-TOKEN"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public CookieCsrfTokenRepository csrfTokenRepository() {
        CookieCsrfTokenRepository repository = CookieCsrfTokenRepository.withHttpOnlyFalse();

        repository.setCookieCustomizer(cookie -> cookie
                .secure(false) // Set to true in production when using HTTPS
                .sameSite("Lax"));

        repository.setHeaderName("X-CSRF-TOKEN");
        repository.setCookiePath("/");
        return repository;
    }
}