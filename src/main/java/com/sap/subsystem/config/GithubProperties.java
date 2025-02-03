package com.sap.subsystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "github")
public class GithubProperties {
    private String owner;
    private String token;

    public String getOwner() {
        return owner;
    }

    public GithubProperties setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public String getToken() {
        return token;
    }

    public GithubProperties setToken(String token) {
        this.token = token;
        return this;
    }
}
