package com.lucassilvs.configserver.config.aws.secrets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucassilvs.configserver.config.aws.secrets.properties.SecretProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Configuration
public class SecretManagerConfig {

    @Value("${aws.secret.name}")
    private String secretName;

    private final ObjectMapper mapper;

    private final SecretsManagerClient secretsManagerClient;

    @Autowired
    public SecretManagerConfig(ObjectMapper mapper, SecretsManagerClient secretsManagerClient) {
        this.mapper = mapper;
        this.secretsManagerClient = secretsManagerClient;
    }

    @Bean(name = "SecretGitCredential")
    public SecretProperties getGitCredential() throws JsonProcessingException {

        GetSecretValueRequest secretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse response = secretsManagerClient.getSecretValue(secretValueRequest);
        SecretProperties secretProperties = mapper.readValue(response.secretString(), SecretProperties.class);

        return secretProperties;
    }
}
