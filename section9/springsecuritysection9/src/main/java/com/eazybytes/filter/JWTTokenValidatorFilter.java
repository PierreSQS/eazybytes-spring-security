package com.eazybytes.filter;

import com.eazybytes.constants.SecurityConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get the Token from the Request Header
        String jwt = request.getHeader(SecurityConstants.JWT_HEADER);

        // if Token exists, validate it
        if (jwt != null) {
            try {
                SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

                // Create JWTParser and parse JWT Token
                JwtParser jwtParser = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build();
                // Read JWT-Claims
                Claims claims = jwtParser.parseClaimsJws(jwt).getBody();

                // Get authenticated User and Authorities from JWT by reading the claims
                String username = String.valueOf(claims.get("username"));
                String authorities = String.valueOf(claims.get("authorities"));

                // Validate the credentials from Claim
                Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

                // Write validated Credential in the Security Context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (WeakKeyException e) {
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                e.printStackTrace();
            } catch (UnsupportedJwtException e) {
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                e.printStackTrace();
            } catch (SignatureException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        // continue with the filter Chain
        filterChain.doFilter(request,response);

    }
}
