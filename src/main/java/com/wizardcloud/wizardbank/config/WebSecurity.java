package com.wizardcloud.wizardbank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurity {
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) ->
            authorize.requestMatchers("/api/**").permitAll()
        );

        return http.build();
    }
}