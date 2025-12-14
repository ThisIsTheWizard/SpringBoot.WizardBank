package com.wizardcloud.wizardbank.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {
    private AccessToken accessToken = new AccessToken();
    private RefreshToken refreshToken = new RefreshToken();
    private SecretToken secret = new SecretToken();

    @Getter
    @Setter
    public static class AccessToken {
        private Token token = new Token();
    }

    @Getter
    @Setter
    public static class RefreshToken {
        private long expiration;
    }

    @Getter
    @Setter
    public static class SecretToken {
        private String key;
    }

    @Getter
    @Setter
    public static class Token {
        private long expirationMs;
    }

}
