//package com.wizardcloud.wizardbank.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//    @Bean
//    protected SecurityFilterChain configure(HttpSecurity http) {
//        http.authorizeHttpRequests(authorize ->
//            authorize
//                .requestMatchers("/").permitAll()
//                .requestMatchers("/api/**").permitAll()
//        );
//
//        return http.build();
//    }
//}