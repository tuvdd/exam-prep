package com.project.exam_prep.config;

import com.project.exam_prep.filter.JwtRequestFilter;
import com.project.exam_prep.service.impl.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((request) -> request
//                trong db luu role la ROLE_STUDENT (STUDENT la loi)

                .requestMatchers("/api/user/admin/**",
                        "/api/admin/**",
                        "/api/question/admin/**",
                        "/api/question-set/admin/**",
                        "/api/quiz/admin/**",
                        "/api/student/admin/**",
                        "/api/teacher/admin/**",
                        "/api/result/admin/**")
                        .hasRole("ADMIN")
                .requestMatchers("/api/question/**",
                        "/api/question-set/**",
                        "/api/quiz/teacher/**",
                        "/api/result/teacher/**",
                        "/api/teacher/**")
                        .hasRole("TEACHER")
                .requestMatchers("/api/quiz/student/**", "api/student/**").hasRole("STUDENT")
                .requestMatchers("/api/quiz/**").hasAnyRole("STUDENT", "TEACHER")
                .requestMatchers("/api/teacher/**").hasAnyRole("ADMIN", "TEACHER")
                .requestMatchers("/api/student/**").hasAnyRole("ADMIN", "STUDENT")
                .requestMatchers("/api/authenticate","/api/user/login").permitAll()
                .anyRequest().authenticated()
        )
                .sessionManagement(sesstion -> sesstion.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//                .formLogin(Customizer.withDefaults())
//                .logout(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
