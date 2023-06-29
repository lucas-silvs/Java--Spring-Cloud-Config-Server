package com.lucassilvs.configserver.config.git;

import com.lucassilvs.configserver.config.aws.secrets.properties.SecretProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class CredentialGitConfiguration {

    private SecretProperties secretProperties;

    @Autowired
    public CredentialGitConfiguration(SecretProperties secretProperties) {
        this.secretProperties = secretProperties;
    }

    @Bean
    @DependsOn("SecretGitCredential")
    public void configureGitCredential() {
        System.setProperty("spring.cloud.config.server.git.username", secretProperties.getUsername());
        System.setProperty("spring.cloud.config.server.git.password", secretProperties.getPassword());
    }
}
