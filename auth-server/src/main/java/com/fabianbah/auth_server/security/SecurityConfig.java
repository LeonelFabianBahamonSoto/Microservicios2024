package com.fabianbah.auth_server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import com.fabianbah.auth_server.services.JwtValidationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtValidationFilter jwtValidationFilter)
            throws Exception {

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        var requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http.authorizeHttpRequests(auth -> auth
                // .requestMatchers("/loans/**", "/balance/**", "/accounts/**",
                // "/cards/**").authenticated()
                .requestMatchers("/loans").hasAuthority("VIEW_LOANS")
                .requestMatchers("/balance").hasAuthority("VIEW_BALANCE")
                .requestMatchers("/accounts").hasAuthority("VIEW_ACCOUNT")
                .requestMatchers("/users").hasAuthority("VIEW_ACCOUNT")
                .requestMatchers("/users/createUser").permitAll()
                .requestMatchers("/cards").hasAnyAuthority("VIEW_CARDS", "VIEW_ACCOUNT")
                .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        http.addFilterAfter(jwtValidationFilter, BasicAuthenticationFilter.class);
        // http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.csrf(csrf -> csrf
                .csrfTokenRequestHandler(requestHandler)
                .ignoringRequestMatchers("/users/createUser", "/welcome", "/aboutUs", "/auth/authenticate", "/auth/validateTokenAuth")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);

        return http.build();
    };

    // ESTE SE MANEJA EN EL GATEWAY Y NO AQUI PORQUE ENTONCES DUPLICA Y FALLA
    // @Bean
    // CorsConfigurationSource corsConfigurationSource() {
    // CorsConfiguration config = new CorsConfiguration();

    // // Permite orígenes específicos (asegúrate de que coincidan exactamente)
    // config.setAllowedOrigins(List.of("http://localhost:4200",
    // "http://localhost:3000", "https://myweb.com"));
    // // Permitir todas las cabeceras
    // config.setAllowedHeaders(List.of("*"));
    // // Permitir todas las credenciales (necesario si envías cookies o tokens)
    // config.setAllowCredentials(true);
    // // Métodos permitidos
    // config.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "PATCH",
    // "OPTIONS"));
    // // Explicita los headers expuestos si los necesitas (ejemplo:
    // `Authorization`)
    // // config.setExposedHeaders(List.of("Authorization"));

    // var source = new UrlBasedCorsConfigurationSource();
    // source.registerCorsConfiguration("/**", config);

    // return source;
    // }

    @Bean
    PasswordEncoder passwordEncoder() {
        // return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // @Bean
    // InMemoryUserDetailsManager inMemoryUserDetailsManager(){
    // UserDetails admin = User
    // .withUsername("fabian")
    // .password("admin")
    // .authorities("ADMIN")
    // .build();

    // UserDetails user = User
    // .withUsername("lucas")
    // .password("user")
    // .authorities("USER")
    // .build();

    // return new InMemoryUserDetailsManager( admin, user );
    // };

    // @Bean
    // UserDetailsService userDetailsService(DataSource dataSource) {
    // return new JdbcUserDetailsManager(dataSource);
    // }
}