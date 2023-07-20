package com.lucassilvs.configserver.config.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@ConfigurationProperties(prefix = "users")
public class ListRolesProperties {

    private ArrayList<RolesProperties> credentials;

    public ArrayList<RolesProperties> getCredentials() {
        return credentials;
    }

    public void setCredentials(ArrayList<RolesProperties> credentials) {
        this.credentials = credentials;
    }
}
