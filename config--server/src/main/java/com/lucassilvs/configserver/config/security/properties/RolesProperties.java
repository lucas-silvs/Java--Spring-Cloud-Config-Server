package com.lucassilvs.configserver.config.security.properties;

import java.util.List;

public record RolesProperties(
        String username,
        String password,
        List<String> roles
) {

    public String rolesToString() {
        return roles.toString().replace("[", "").replace("]", "");
    }
}
