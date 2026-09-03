package com.commercehub.api_gateway.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.nimbusds.jose.util.Base64;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Configuration
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(
                                "/api/v1/auth/register",
                                "/api/v1/auth/login",
                                "/api/v1/auth/refresh"
                        ).permitAll()

                        .pathMatchers("/api/v1/auth/admin")
                        .hasRole("ADMIN")

                        // ---------- USER SERVICE ----------
                        .pathMatchers(HttpMethod.GET, "/api/v1/users/**")
                        .hasAnyRole("USER", "ADMIN")

                        .pathMatchers(HttpMethod.PUT, "/api/v1/users/**")
                        .hasAnyRole("USER", "ADMIN")

                        .pathMatchers(HttpMethod.DELETE, "/api/v1/users/**")
                        .hasRole("ADMIN")

                        // ---------- PRODUCT SERVICE ----------
                        .pathMatchers(HttpMethod.GET, "/api/v1/products/**")
                        .hasAnyRole("USER", "ADMIN")

                        .pathMatchers(HttpMethod.POST, "/api/v1/products/**")
                        .hasRole("ADMIN")

                        .pathMatchers(HttpMethod.PUT, "/api/v1/products/**")
                        .hasRole("ADMIN")

                        .pathMatchers(HttpMethod.PATCH, "/api/v1/products/**")
                        .hasRole("ADMIN")

                        // ---------- INVENTORY SERVICE ----------
                        .pathMatchers("/api/v1/inventory/**")
                        .hasRole("ADMIN")

                        // ---------- ORDER SERVICE ----------
                        .pathMatchers("/api/v1/orders/**")
                        .hasAnyRole("USER", "ADMIN")

                        .anyExchange().authenticated()

//                        .pathMatchers(HttpMethod.POST,"/api/v1/products")
//                        .hasRole("ADMIN")
//                        .pathMatchers(HttpMethod.GET, "/api/v1/products/**")
//                        .authenticated()
//
//                        .anyExchange()
//                        .authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )
                .build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {

        byte[] keyBytes = Base64.from(jwtSecret).decode();

        SecretKey key =
                new SecretKeySpec(keyBytes, "HmacSHA256");

        return NimbusReactiveJwtDecoder
                .withSecretKey(key)
                .build();
    }

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>>
    jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter authoritiesConverter =
                new JwtGrantedAuthoritiesConverter();

        authoritiesConverter.setAuthoritiesClaimName("role");
        authoritiesConverter.setAuthorityPrefix("");

        return jwt -> {
            Collection<GrantedAuthority> authorities =
                    authoritiesConverter.convert(jwt);

            return Mono.just(
                    new JwtAuthenticationToken(
                            jwt,
                            authorities,
                            jwt.getSubject()
                    )
            );
        };
    }
}