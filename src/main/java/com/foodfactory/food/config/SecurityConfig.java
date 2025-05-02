package com.foodfactory.food.config;

import com.foodfactory.food.services.CustomerUserDetailsService;
import com.foodfactory.food.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomerUserDetailsService customerUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(CustomerUserDetailsService customerUserDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customerUserDetailsService = customerUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("admin")
                .password(passwordEncoder().encode("password")) // Use BCrypt
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    @Order(1)
    public SecurityFilterChain customerSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/menu/customer", "/api/cart/**", "/register/**", "/customers/new", "/")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/menu/customer", "/api/cart/**", "/login/**", "/register/**", "/menu_interface", "/customers/new", "/", "/error", "/webjars/**", "/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(customerUserDetailsService)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Order(2)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/users/**", "/customers/**", "/menu/**", "login", "menu_interface", "error")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/users/**", "/menu/**").hasAuthority("ADMIN")
                        .requestMatchers("/customers/**").permitAll()
                .requestMatchers("/webjars/**", "/css/**", "/js/**", "/images/**", "/login", "admin_login").permitAll()
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                .loginPage("/admin_login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/menu_interface", true)
                .failureUrl("/admin_login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
        )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/menu_interface")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/403")
                )
                .userDetailsService(customerUserDetailsService)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}

