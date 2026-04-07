package com.tradebot.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private static final String LOGIN_PATH = "/login";
    private static final String REGISTER_PATH = "/register";
    private static final String LOGOUT_PATH = "/logout";
    private static final String DASHBOARD_PATH = "/dashboard";

    private static void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(LOGIN_PATH, REGISTER_PATH).permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(SecurityConfig::customize
        );
        http.cors(Customizer.withDefaults());
        http.formLogin(form -> form
                .loginPage(LOGIN_PATH)
                .loginProcessingUrl(LOGIN_PATH)
                .defaultSuccessUrl(DASHBOARD_PATH, true)
                .failureUrl(LOGIN_PATH + "?error=true")
        );
        http.logout(logout -> logout
                .logoutUrl(LOGOUT_PATH)
                .logoutSuccessUrl(LOGIN_PATH + "?logout=true")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

@Controller
class PageController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin-page")
    public String adminPage() {
        return "adminPage";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user-page")
    public String userPage() {
        return "userPage";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
