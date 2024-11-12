package com.fabianbah.auth_server.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class SecurityConfig {

    private static final String[] USER_RESOURCES = { "/loans/**", "/balance/**" };
    private static final String[] ADMIN_RESOURCES = { "/accounts/**", "/cards/**" };
    private static final String AUTH_WRITE = "write";
    private static final String AUTH_READ = "read";
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";
    private static final String LOGIN_RESOURCE = "/login";
    private static final String RSA = "RSA";
    private static final Integer RSA_SIZE = 2048;
    private static final String APPLICATION_OWNER = "Debuggeando ideas";

    @Bean
    @Order(1)
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

        http.exceptionHandling(e -> e.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(LOGIN_RESOURCE)));

        return http.build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain clientSecurityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(Customizer.withDefaults());
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(ADMIN_RESOURCES).hasAuthority(AUTH_WRITE)
                .requestMatchers(USER_RESOURCES).hasAuthority(AUTH_READ)
                .anyRequest().permitAll());
        http.oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    @Order(3)
    SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(ADMIN_RESOURCES).hasRole(ROLE_ADMIN)
                .requestMatchers(USER_RESOURCES).hasRole(ROLE_USER)
                .anyRequest().permitAll());
        http.oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider(PasswordEncoder encoder,
            CustomerUserDetails customerUserDetails) throws Exception {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(encoder);
        authProvider.setUserDetailsService(customerUserDetails);

        return authProvider;
    }

    @Bean
    AuthorizationServerSettings authorizationServerSettings() throws Exception {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() throws Exception {
        var converter = new JwtGrantedAuthoritiesConverter();

        converter.setAuthorityPrefix("");

        return converter;
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(JwtGrantedAuthoritiesConverter authorizationServerSettings)
            throws Exception {
        var converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(authorizationServerSettings);

        return converter;
    }

    @Bean
    JWKSource<SecurityContext> jwkSource() {
        var rsa = generateKeys();
        var jwkSet = new JWKSet(rsa);

        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    OAuth2TokenCustomizer<JwtEncodingContext> oAuth2TokenCustomizer() {
        return context -> {
            if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
                context.getClaims().claims(claim -> claim.putAll(Map.of(
                        "owner", APPLICATION_OWNER,
                        "date_request", LocalDateTime.now().toString())));
            }
        };
    }

    private static KeyPair generateRSA() {
        KeyPair keyPair;

        try {
            var keyPairGenerator = KeyPairGenerator.getInstance(RSA);
            keyPairGenerator.initialize(RSA_SIZE);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }

        return keyPair;
    }

    private static RSAKey generateKeys() {
        KeyPair keyPair = generateRSA();
        var publicKey = (RSAPublicKey) keyPair.getPublic();
        var privateKey = (RSAPrivateKey) keyPair.getPrivate();

        return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
    }

}
