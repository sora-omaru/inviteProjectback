package com.omaru.wedding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 1) ゲスト用：invites は無条件で公開（最優先）
    @Bean
    @Order(1)
    SecurityFilterChain invitesChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/v1/invites/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }

    // 2) 管理用：admin は Basic 必須、それ以外は閉じる
    @Bean
    @Order(2)
    SecurityFilterChain adminChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/v1/admin/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    // 3) その他：全部閉じる（安全側）
    @Bean
    @Order(3)
    SecurityFilterChain denyAllChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().denyAll())
                .build();
    }
}