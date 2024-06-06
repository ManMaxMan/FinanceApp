package com.github.ManMaxMan.FinanceApp.serviceUser.controller.filter;

import com.github.ManMaxMan.FinanceApp.serviceUser.service.security.UserDetailsServiceImpl;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.utils.JwtTokenHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenHandler jwtTokenHandler;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtFilter(JwtTokenHandler jwtTokenHandler, UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenHandler = jwtTokenHandler;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractToken(request);
        if (token != null && jwtTokenHandler.validate(token)) {
            String email = jwtTokenHandler.getMail(token);
            Authentication authentication = createAuthentication(email);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
