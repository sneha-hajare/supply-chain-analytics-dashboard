
package com.example.supplychain.config;

import com.example.supplychain.service.CustomUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider authenticationProvider)
            throws Exception {

        http
                .authenticationProvider(authenticationProvider)

                .authorizeHttpRequests(auth -> auth

                        // Public login page and static resources
                        .requestMatchers(
                                "/login",
                                "/css/**",
                                "/js/**"
                        ).permitAll()

                        // Only Admin can access user-management pages
                        .requestMatchers("/admin", "/admin/**")
                        .hasRole("ADMIN")

                        // All three roles can view analytics
                        .requestMatchers(
                                "/supplier-analytics/**",
                                "/forecast/**"
                        ).hasAnyRole("ADMIN", "MANAGER", "ANALYST")

                        // Only Admin and Manager can access these modules
                        .requestMatchers(
                                "/suppliers/**",
                                "/inventory/**",
                                "/orders/**"
                        ).hasAnyRole("ADMIN", "MANAGER")

                        // All other pages require login
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}
