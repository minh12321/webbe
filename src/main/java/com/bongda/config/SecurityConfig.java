package com.bongda.config;

   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.security.authentication.AuthenticationManager;
   import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
   import org.springframework.security.config.annotation.web.builders.HttpSecurity;
   import org.springframework.security.config.http.SessionCreationPolicy;
   import org.springframework.security.core.userdetails.UserDetailsService;
   import org.springframework.security.web.SecurityFilterChain;
   import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
   import org.springframework.web.cors.CorsConfiguration;
   import org.springframework.web.cors.CorsConfigurationSource;
   import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

   import java.util.List;

   @Configuration
   public class SecurityConfig {

       @Autowired
       private UserDetailsService userDetailsService;

       @Autowired
       private JwtRequestFilter jwtRequestFilter;

       @Bean
       public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
           http
               .cors(cors -> cors.configurationSource(corsConfigurationSource()))
               .csrf(csrf -> csrf.disable())
               .authorizeHttpRequests(auth -> auth
                   .requestMatchers("/shopbongda/auth/**", "/shopbongda/login/oauth2/**","/shopbongda/api/products","/shopbongda/api/hoadon","/shopbongda/api/khachhang","/shopbongda/api/hoadon/makh/**").permitAll()
                   .requestMatchers("/shopbongda/admin/**").hasAuthority("ADMIN")
                   .anyRequest().authenticated()
               )
               .sessionManagement(session -> session
                   .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               );

           http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
           return http.build();
       }

       @Bean
       public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
           return authenticationConfiguration.getAuthenticationManager();
       }

       @Bean
       public CorsConfigurationSource corsConfigurationSource() {
           CorsConfiguration configuration = new CorsConfiguration();
           configuration.setAllowedOrigins(List.of("https://ab-mocha.vercel.app", "http://localhost:4200"));
           configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
           configuration.setAllowedHeaders(List.of("*"));
           configuration.setAllowCredentials(true);
           UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
           source.registerCorsConfiguration("/**", configuration);
           return source;
       }
   }