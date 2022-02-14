package com.gmail.andersoninfonet.appsecurityclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf()
            .disable()
            .authorizeHttpRequests()
            .antMatchers("/register", "/verifyRegistration", "/resendVerifyToken").permitAll()
            .antMatchers("/api/**").authenticated()
            .and()
            .oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/api-client-oidc"))
            .oauth2Client(Customizer.withDefaults());
        return http.build();
    }
}
