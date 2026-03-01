package com.omaru.wedding.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // フロントのURL（Next dev）
        config.setAllowedOrigins(List.of("http://localhost:3000"));

        // 必要なHTTPメソッド（OPTIONSはプリフライトで必須）
        config.setAllowedMethods(List.of("GET", "PATCH", "POST", "OPTIONS"));

        // fetchで送るヘッダ
        config.setAllowedHeaders(List.of("Content-Type", "Authorization"));

        // cookie使わないなら false のままでOK
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // API配下に適用
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }
}