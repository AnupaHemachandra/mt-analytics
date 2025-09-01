package com.mt_analytics.mt_analytics.filters;

import com.mt_analytics.mt_analytics.service.JwtService;
import com.mt_analytics.mt_analytics.service.UserInfoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver exceptionResolver;
  private final JwtService jwtService;
  private final UserInfoService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
    // Retrieve the Authorization header
    String authHeader = request.getHeader("Authorization");

    // Check if the header starts with "Bearer "
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7); // Extract token
      String username = jwtService.extractUsername(token); // Extract username fr
      // om token
      // If the token is valid and no authentication is set in the context
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Validate token and set authentication
        if (jwtService.validateToken(token, userDetails)) {
          UsernamePasswordAuthenticationToken authToken =
                  new UsernamePasswordAuthenticationToken(
                          userDetails, null, userDetails.getAuthorities());
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }
    }

    // Continue the filter chain
    filterChain.doFilter(request, response);
    } catch (Exception ex) {
      exceptionResolver.resolveException(request, response, null, ex);
    }
  }
}
