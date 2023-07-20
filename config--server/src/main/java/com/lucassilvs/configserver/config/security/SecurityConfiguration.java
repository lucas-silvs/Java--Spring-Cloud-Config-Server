package com.lucassilvs.configserver.config.security;

import com.lucassilvs.configserver.config.security.encoder.CustomPasswordEncoder;
import com.lucassilvs.configserver.config.security.properties.ListRolesProperties;
import org.springframework.beans.factory.annotation.Autowired;
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

    private ListRolesProperties rolesProperties;

    @Autowired
    public SecurityConfiguration(ListRolesProperties rolesProperties) {
        this.rolesProperties = rolesProperties;
    }

    @Bean
    public UserDetailsService userDetailsService() {


        PasswordEncoder encoder = new CustomPasswordEncoder();

        List<UserDetails> userDetailsList = rolesProperties.getCredentials().stream().map(role -> User.withUsername(role.username())
                .password(encoder.encode(role.password()))
                .roles(role.rolesToString())
                .build()).toList();

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
