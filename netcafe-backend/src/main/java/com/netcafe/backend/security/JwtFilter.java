package com.netcafe.backend.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring("Bearer ".length());
            try {
                Claims claims = jwtUtil.parse(token);
                String sub = claims.getSubject();
                String role = claims.get("role", String.class);
                if (role == null) role = "USER";

                var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
                var authentication = new UsernamePasswordAuthenticationToken(sub, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception ignored) {
                // invalid token -> ignore and proceed unauthenticated
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/") || path.startsWith("/ws");
    }
}
