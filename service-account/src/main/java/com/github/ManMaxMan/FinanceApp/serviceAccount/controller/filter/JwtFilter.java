package com.github.ManMaxMan.FinanceApp.serviceAccount.controller.filter;

import com.github.ManMaxMan.FinanceApp.serviceAccount.controller.filter.feign.api.UserClientFeign;
import com.github.ManMaxMan.FinanceApp.serviceAccount.controller.filter.feign.dto.UserDTO;
import com.github.ManMaxMan.FinanceApp.serviceAccount.controller.filter.feign.enums.EUserRole;
import com.github.ManMaxMan.FinanceApp.serviceAccount.controller.utils.JwtTokenHandler;
import com.github.ManMaxMan.FinanceApp.serviceAccount.controller.utils.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenHandler jwtHandler;

    private final UserClientFeign userClientFeign;


    public JwtFilter(JwtTokenHandler jwtHandler, UserClientFeign userClientFeign) {
        this.jwtHandler = jwtHandler;
        this.userClientFeign = userClientFeign;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        if (!jwtHandler.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        // Get user identity and set it on the spring security context
        // 2 Варианта
        // 1. Сходить на урл /me положив в хэадер токен.
        // Получить данные на user-service через http ответ разобрать и поместить
        // в переменную userDetails.
        // 2. Изначально поместить всё в токен и доставать всё из токена

        String newToken = jwtHandler.generateAccessToken(jwtHandler.getUsername(token),
                EUserRole.SYSTEM);

        ResponseEntity<UserDTO> userDTOResponse = userClientFeign.
                aboutUser("Bearer "+newToken);

        if (userDTOResponse==null|| userDTOResponse.getBody()==null){
            chain.doFilter(request, response);
            return;
        }


        UserDetailsImpl userDetails = new UserDetailsImpl(userDTOResponse.getBody(), header);

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ?
                        List.of() : userDetails.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}