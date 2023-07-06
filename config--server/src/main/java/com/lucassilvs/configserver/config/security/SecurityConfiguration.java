package com.lucassilvs.configserver.config.security;

import com.lucassilvs.configserver.config.security.encoder.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Value("${security.username}")
    private String username;

    @Value("${security.password}")
    private String password;

    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = new CustomPasswordEncoder();

        UserDetails userDetails = User.withUsername(username)
                .password(encoder.encode(password))
                .roles("APP")
                .build();

        UserDetails userDetailsMonitoring = User.withUsername("monitoring")
                .password(encoder.encode("monitoring"))
                .roles("MONITORING", "OPERATOR")
                .build();

        UserDetails operator = User.withUsername("operator")
                .password(encoder.encode("operator"))
                .roles("OPERATOR")
                .build();

        List<UserDetails> userDetailsList = List.of(userDetails, userDetailsMonitoring, operator);

        return new InMemoryUserDetailsManager(userDetailsList);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http
               .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/actuator/**", "/actuator/").hasAuthority("ROLE_MONITORING")
                                .requestMatchers("/encrypt/**", "/decrypt/**").hasAuthority("ROLE_OPERATOR")
                                .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults());
       return http.build();
    }
}
