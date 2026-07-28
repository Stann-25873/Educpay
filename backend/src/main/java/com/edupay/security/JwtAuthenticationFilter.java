package com.edupay.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authHeader.substring("Bearer ".length()).trim();
            if (token.isEmpty() || !jwtTokenProvider.isTokenValid(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = jwtTokenProvider.parseAllClaims(token);
            String typ = Objects.toString(claims.get("typ"), null);
            if (!"access".equals(typ)) {
                // Token is cryptographically valid but wrong type
                filterChain.doFilter(request, response);
                return;
            }

            String tenantId = Objects.toString(claims.get("tenant_id"), null);
            String subject = claims.getSubject();
            if (tenantId == null || tenantId.isBlank() || subject == null || subject.isBlank()) {
                filterChain.doFilter(request, response);
                return;
            }

            // Authorities from roles claim if present; else empty.
            // Must remain server-side: token must include role information.
            Object rolesObj = claims.get("roles");
            Collection<SimpleGrantedAuthority> authorities = parseAuthorities(rolesObj);

            TenantContextHolder.setTenantId(tenantId);
            User principal = new User(subject, "", authorities);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(principal, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } finally {
            // Prevent tenant data leakage across requests
            TenantContextHolder.clear();
        }
    }

    private Collection<SimpleGrantedAuthority> parseAuthorities(Object rolesObj) {
        // Expected formats:
        // - List<String>
        // - String roles separated by commas
        if (rolesObj == null) {
            return List.of();
        }
        if (rolesObj instanceof List<?> list) {
            return list.stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                    .map(SimpleGrantedAuthority::new)
                    .toList();
        }
        if (rolesObj instanceof String s) {
            if (s.isBlank()) return List.of();
            return List.of(s.split(","))
                    .stream()
                    .map(String::trim)
                    .filter(x -> !x.isBlank())
                    .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                    .map(SimpleGrantedAuthority::new)
                    .toList();
        }

        return List.of();
    }
}


