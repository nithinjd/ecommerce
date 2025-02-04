package com.example.OrderService.intercept;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
@Configuration
public class OAuthRequestInterceptor implements RequestInterceptor {
    private OAuth2AuthorizedClientManager clientManager;

    public OAuthRequestInterceptor(OAuth2AuthorizedClientManager clientManager) {
        this.clientManager = clientManager;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = clientManager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("internal-client").principal("internal").build()).getAccessToken().getTokenValue();

        requestTemplate.header("Authorization", "Bearer"+token);


    }
}
