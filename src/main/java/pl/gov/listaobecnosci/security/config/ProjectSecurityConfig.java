package pl.gov.listaobecnosci.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .cors().configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setExposedHeaders(List.of("Authorization"));
                    config.setMaxAge(3600L);
                    return config;
                }).and().csrf().ignoringRequestMatchers(new AntPathRequestMatcher("/"))
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/api/freedays"))
                .hasAnyRole("AUTHORIZED_USER", "ADMINISTRATOR")
                .requestMatchers(new AntPathRequestMatcher("/api/sections"))
                .hasAnyRole("AUTHORIZED_USER", "ADMINISTRATOR")
                .requestMatchers(new AntPathRequestMatcher("/api/roles")).hasRole("ADMINISTRATOR")
                .requestMatchers(new AntPathRequestMatcher("/api/users")).hasRole("ADMINISTRATOR")
                .requestMatchers(new AntPathRequestMatcher("/api/workers"))
                .hasAnyRole("AUTHORIZED_USER", "ADMINISTRATOR")
                .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                .and().oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter);
        return http.build();
    }
}
