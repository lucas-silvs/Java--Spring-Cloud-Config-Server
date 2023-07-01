package com.lucassilvs.configserver.config.git;

import com.lucassilvs.configserver.config.aws.secrets.properties.SecretProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import static com.lucassilvs.configserver.enums.GitCredentialsEnums.GIT_PASSWORD;
import static com.lucassilvs.configserver.enums.GitCredentialsEnums.GIT_USERNAME;

@Configuration
public class CredentialGitConfiguration {

    private final SecretProperties secretProperties;

    @Autowired
    public CredentialGitConfiguration(SecretProperties secretProperties) {
        this.secretProperties = secretProperties;
    }

    @Bean
    @DependsOn("SecretGitCredential")
    public void configureGitCredential() {

        System.setProperty(GIT_USERNAME.getValue(), secretProperties.getUsername());
        System.setProperty(GIT_PASSWORD.getValue(), secretProperties.getPassword());
    }
}
