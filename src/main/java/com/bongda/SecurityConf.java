package com.bongda;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConf {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/shopbongda/**").permitAll() // Cho phép truy cập không cần xác thực
                .anyRequest().authenticated() // Các endpoint khác yêu cầu xác thực
            );
        return http.build();
    }
}
