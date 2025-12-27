package com.example.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import
org.springframework.security.config.annotation.authentication.configuration.Authentic
ationConfiguration;
import
org.springframework.security.config.annotation.method.configuration.EnableMethodS
ecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import
org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigure
r;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
@Bean
public PasswordEncoder passwordEncoder() {
return new BCryptPasswordEncoder();
}
@Bean
public AuthenticationManager authenticationManager(
AuthenticationConfiguration config) throws Exception {
return config.getAuthenticationManager();
}
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
http
.csrf(AbstractHttpConfigurer::disable)
.sessionManagement(session ->
session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
)
.authorizeHttpRequests(auth -> auth
.requestMatchers(
"/api/auth/**",
"/swagger-ui/**",
"/v3/api-docs/**",
"/swagger-resources/**",
"/webjars/**"
).permitAll()
// 膆 ADDED: /api/recommendations/** to the permitted list
.requestMatchers(
"/api/users/**",
"/cards/**",
"/api/reward-rules/**",
"/api/intents/**",
"/api/recommendations/**"
).permitAll()
.anyRequest().authenticated()
);
return http.build();
}
}