package com.lucassilvs.configserver.config.aws.secrets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucassilvs.configserver.config.aws.secrets.properties.SecretProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Configuration
public class SecretClientConfiguration {
    @Bean
    @Profile("aws")
    public SecretsManagerClient secretManagerClientAws() {
        return SecretsManagerClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.US_EAST_1)
                .build();
    }

    @Bean
    @Profile("localstack")
    public SecretsManagerClient secretManagerClientLocalstack() throws JsonProcessingException {
        return SecretsManagerClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.US_EAST_1)
                .endpointOverride(java.net.URI.create("http://localhost:4566"))
                .build();
    }
}
