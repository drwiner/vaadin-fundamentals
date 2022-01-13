package org.vaadin.example.security;

import com.auth0.AuthenticationController;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

public abstract class AuthenticationControllerProvider {

    private static AuthenticationController authenticationController;

    public static AuthenticationController getInstance() throws UnsupportedEncodingException {
        if (authenticationController == null) {
            Properties auth0Properties = Auth0Util.getAuth0Properties();
            String domain = auth0Properties.getProperty("auth0.domain");
            String clientId = auth0Properties.getProperty("auth0.clientId");
            String clientSecret = auth0Properties.getProperty("auth0.clientSecret");


            if (domain == null || clientId == null || clientSecret == null) {
                throw new IllegalArgumentException("Missing domain, clientId, or clientSecret. Did you update src/main/webapp/WEB-INF/web.xml?");
            }

            // JwkProvider required for RS256 tokens. If using HS256, do not use.
            JwkProvider jwkProvider = new JwkProviderBuilder(domain).build();
            authenticationController = AuthenticationController.newBuilder(domain, clientId, clientSecret)
                    .withJwkProvider(jwkProvider)
                    .build();
        }
        return authenticationController;
    }

}
