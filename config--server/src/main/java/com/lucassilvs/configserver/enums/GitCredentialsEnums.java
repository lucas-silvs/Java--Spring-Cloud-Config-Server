package com.lucassilvs.configserver.enums;

public enum GitCredentialsEnums {

    GIT_USERNAME("spring.cloud.config.server.git.username"),
    GIT_PASSWORD("spring.cloud.config.server.git.password");

    private final String value;

    GitCredentialsEnums(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
