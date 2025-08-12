package es.agonzalez.incident.monitor.configurations;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.config.Customizer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import es.agonzalez.incident.monitor.security.JwtAuthFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtAuthFilter filter;
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.cors(c -> {});
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/health", "/api/v1/auth/**").permitAll()
            .anyRequest().authenticated()
        );
        
        http.addFilterBefore(filter, BasicAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults());
        return http.build();

    }
}
