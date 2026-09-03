package com.commercehub.api_gateway.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.server.reactive.ServerHttpRequest;

import reactor.core.publisher.Mono;

@Configuration
public class GatewayIdentityConfig {

    @Bean
    public GlobalFilter userIdentityFilter() {

        return (exchange, chain) ->

                ReactiveSecurityContextHolder.getContext()
                        .map(SecurityContext::getAuthentication)
                        .flatMap(authentication -> {

                            String username = authentication.getName();

                            String role = authentication.getAuthorities()
                                    .stream()
                                    .findFirst()
                                    .map(Object::toString)
                                    .orElse("");

                            ServerHttpRequest request =
                                    exchange.getRequest()
                                            .mutate()
                                            .header("X-User-Name", username)
                                            .header("X-User-Role", role)
                                            .build();

                            return chain.filter(
                                    exchange.mutate()
                                            .request(request)
                                            .build()
                            );
                        })
                        .switchIfEmpty(chain.filter(exchange));
    }
}