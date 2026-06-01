package com.library.system.config;

import org.springframework.security.config.http.SessionCreationPolicy; //opgx
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails member = User.withUsername("member")
                .password(passwordEncoder.encode("member123"))
                .roles("MEMBER")
                .build();

        return new InMemoryUserDetailsManager(admin, member);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                // Tarayıcı çerez çakışmalarını ve tekrar şifre isteme kutusunu engellemek için ekledik
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // Hazırladığımız ön yüzün tarayıcıda engelsiz açılması için en üste ekledik
                        .requestMatchers("/", "/index.html").permitAll()

                        // Kitap işlemleri yetkilendirmeleri
                        .requestMatchers(HttpMethod.GET, "/api/books/**").hasAnyRole("ADMIN", "MEMBER")
                        .requestMatchers(HttpMethod.POST, "/api/books/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN")

                        // Üye işlemleri yetkilendirmeleri
                        .requestMatchers("/api/members/**").hasRole("ADMIN")

                        // Ödünç alma (Loan) işlemleri yetkilendirmeleri
                        .requestMatchers("/api/loans/borrow").hasAnyRole("ADMIN", "MEMBER")
                        .requestMatchers("/api/loans/return/**").hasAnyRole("ADMIN", "MEMBER")
                        .requestMatchers("/api/loans/active").hasRole("ADMIN")
                        .requestMatchers("/api/loans/member/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}