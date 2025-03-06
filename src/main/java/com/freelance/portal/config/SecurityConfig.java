package com.freelance.portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class for the Freelance Job Portal application.
 * This class configures security settings, including authentication and authorization.
 */
@EnableWebSecurity
public class SecurityConfig {
    /**
     * Configures the security filter chain for the application.
     * @param http HttpSecurity object to configure security settings.
     * @return SecurityFilterChain object with the configured settings.
     * @throws Exception if an error occurs during configuration.
     */
    /**
     * Configures the security filter chain for the application.
     * @param http HttpSecurity object to configure security settings.
     * @return SecurityFilterChain object with the configured settings.
     * @throws Exception if an error occurs during configuration.
     */
    /**
     * Configures the security filter chain for the application.
     * @param http HttpSecurity object to configure security settings.
     * @return SecurityFilterChain object with the configured settings.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/welcome").authenticated() // Secure welcome page
                        .anyRequest().permitAll()) // Allow all other requests
                .formLogin(form -> form.defaultSuccessUrl("/api/welcome", true) // Redirect to welcome page after login
                        .permitAll())
                .logout(logout -> logout.permitAll()); // Allow logout for all users

        return http.build();
    }

    /**
     * Provides a BCryptPasswordEncoder bean for password encryption.
     * @return BCryptPasswordEncoder instance.
     */
    /**
     * Provides a BCryptPasswordEncoder bean for password encryption.
     * @return BCryptPasswordEncoder instance.
     */
    /**
     * Provides a BCryptPasswordEncoder bean for password encryption.
     * @return BCryptPasswordEncoder instance.
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures an in-memory user details service with a default user.
     * @return UserDetailsService instance with the configured user.
     */
    /**
     * Configures an in-memory user details service with a default user.
     * @return UserDetailsService instance with the configured user.
     */
    /**
     * Configures an in-memory user details service with a default user.
     * @return UserDetailsService instance with the configured user.
     */
    @Bean
    UserDetailsService userDetailsService() {
        UserDetails user = User.builder().username("user").password(passwordEncoder().encode("password")) // Encrypt password
                .roles("USER").build();
        return new InMemoryUserDetailsManager(user); // Return in-memory user details manager
    }
}
