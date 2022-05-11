package com.eazybytes.filter;

import com.eazybytes.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Get the Credentials of the authenticated User from the Security Context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // if authenticated, generate the JWT-Token
        if (authentication != null) {
            // generate the secret key for the signature
            SecretKey secret = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

            // generate the JWT-Token
            String jwt = Jwts.builder().setIssuer("Eazy Bank")
                    .setSubject("JWT Token")
                    .claim("username",authentication.getName())
                    .claim("authorities",authoritiesToString(authentication))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + 30000))
                    .signWith(secret)
                    .compact();

            // Set the Token in the Header of the Response
            response.setHeader(SecurityConstants.JWT_HEADER,jwt);
        }

        // continue with the Filter Chain
        filterChain.doFilter(request,response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/user");
    }

    private String authoritiesToString(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }
}
