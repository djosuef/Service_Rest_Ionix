package com.ionix.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.config.Customizer; 

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(CustomAccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Configura la autenticación para una ruta específica
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/insert", "/api/delete_user/**").authenticated()  // Protege estas rutas
                .anyRequest().permitAll()  // Permite acceso a las demás rutas sin autenticación
            )
            .csrf(csrf -> {
                csrf.ignoringRequestMatchers("/api/insert", "/api/delete_user/**", "/api/cifrar/**");  // Ignora CSRF para esta ruta
            })
            .exceptionHandling(exceptionHandling ->
                    exceptionHandling
                        .accessDeniedHandler(this.accessDeniedHandler)
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            )
            .httpBasic(Customizer.withDefaults());  // Usa autenticación básica (usuario y contraseña)
        

        return http.build();
    }

    // Configuración de CORS
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedOrigins("*")
                        .allowedHeaders("*");
            }
        };
    }
}
