package com.company.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    private static final String OAUTH_SCHEME_NAME = "oAuth";
    private static final String PROTOCOL_URL_FORMAT = "%s/realms/%s/protocol/openid-connect";

    private final KeycloakProperties keycloakProperties;

    public SwaggerConfig(KeycloakProperties keycloakProperties) {
        this.keycloakProperties = keycloakProperties;
    }

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(getInfo())
                .components(new Components()
                        .addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme()))
                .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME));
    }

    private Info getInfo() {
        return new Info()
                .title("Ticketing App Rest")
                .description("Api documentation")
                .version("v1.0");
    }

    private SecurityScheme createOAuthScheme() {
        OAuthFlows flows = createOAuthFlows();

        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(flows);
    }

    private OAuthFlows createOAuthFlows() {
        OAuthFlow flow = createAuthorizationCodeFlow();

        return new OAuthFlows()
                .authorizationCode(flow);
    }

    private OAuthFlow createAuthorizationCodeFlow() {
        var protocolUrl = String.format(PROTOCOL_URL_FORMAT,
                keycloakProperties.getAuthServerUrl(), keycloakProperties.getRealm());

        return new OAuthFlow()
                .authorizationUrl(protocolUrl + "/auth")
                .tokenUrl(protocolUrl + "/token")
                .scopes(new Scopes().addString("openid", ""));
    }
}