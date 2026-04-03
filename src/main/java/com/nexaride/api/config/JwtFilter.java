package com.nexaride.api.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // ✅ Skip ALL auth endpoints safely
        if (path.startsWith("/api/auth")) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        // ✅ No token → continue (Spring will block if needed)
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String token = header.substring(7);

            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    );

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            // ✅ DO NOT break flow — let Spring decide
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }

        chain.doFilter(request, response);
    }
}