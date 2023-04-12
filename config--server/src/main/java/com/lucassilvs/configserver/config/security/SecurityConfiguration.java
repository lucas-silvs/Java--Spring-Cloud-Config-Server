package com.lucassilvs.configserver.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Value("${spring.security.username}")
    private String username;

    @Value("${spring.security.password}")
    private String password;

    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails userDetails = User.withUsername(username)
                .password(encoder.encode(password))
                .roles("APPLICATION")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

    /*
    definindo validação de credencial e seus endpoints
     */
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/actuator/**")
                .permitAll()
                .requestMatchers("/actuator")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated();
        return http.build();
    }
}
