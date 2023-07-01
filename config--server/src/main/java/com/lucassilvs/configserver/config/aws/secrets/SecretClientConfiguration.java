package com.lucassilvs.configserver.config.aws.secrets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

@Configuration
public class SecretClientConfiguration {
    @Bean
    @Profile("aws")
    public SecretsManagerClient secretManagerClientAws() {
        return SecretsManagerClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @Bean
    @Profile("localstack")
    public SecretsManagerClient secretManagerClientLocalstack() {
        return SecretsManagerClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .endpointOverride(java.net.URI.create("http://localhost:4566"))
                .build();
    }
}
