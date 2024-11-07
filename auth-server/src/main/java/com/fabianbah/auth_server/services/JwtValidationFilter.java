package com.fabianbah.auth_server.services;

import java.io.IOException;
import java.util.Objects;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fabianbah.auth_server.security.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class JwtValidationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final JwtUserDetailsService jwtUserDetailsService;

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_HEADER_BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final var requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);
        String userName = null;
        String jwt = null;

        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith(AUTHORIZATION_HEADER_BEARER)) {
            jwt = requestTokenHeader.substring(7);

            try {
                userName = jwtService.getUsernameFromToken(jwt);
            } catch (IllegalArgumentException e) {
                log.info("IllegalArgumentException Error: " + e.getMessage());
            } catch (ExpiredJwtException e) {
                log.info("ExpiredJwtException Error: " + e.getMessage());
            }

            if (Objects.nonNull(userName) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                final var userDetails = this.jwtUserDetailsService.loadUserByUsername(userName);

                if (this.jwtService.validateToken(jwt, userDetails)) {
                    var userNameAndPasswordToken = new UsernamePasswordAuthenticationToken(userName, null,
                            userDetails.getAuthorities());

                    userNameAndPasswordToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(userNameAndPasswordToken);
                }
            }
            filterChain.doFilter(request, response);

        }
    }
}
