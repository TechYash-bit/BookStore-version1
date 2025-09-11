package com.TechYash_Bit.onlineBookStore.security;

import com.TechYash_Bit.onlineBookStore.Entities.UserEntity;
import com.TechYash_Bit.onlineBookStore.Repositories.UserRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserRepo userRepo;
    private final JwtTokens jwtTokens;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

       try {
           final String tokenHeader = request.getHeader("Authorization");

           if (tokenHeader == null || !tokenHeader.startsWith("Bearer")) {
               filterChain.doFilter(request, response);
               return;
           }

           String token = tokenHeader.substring(7); // remove "Bearer "
           String username = jwtTokens.getUserNameFromeToken(token);

           if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
               UserEntity user = userRepo.findByUserName(username).orElseThrow();

               UsernamePasswordAuthenticationToken authToken =
                       new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

               SecurityContext context = SecurityContextHolder.createEmptyContext();
               context.setAuthentication(authToken);
               SecurityContextHolder.setContext(context);
           }

           filterChain.doFilter(request, response);
       }
       catch (Exception e){
           handlerExceptionResolver.resolveException(request,response,null,e);
       }

    }
}
