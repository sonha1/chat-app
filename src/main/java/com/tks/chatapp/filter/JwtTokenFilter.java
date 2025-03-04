package com.tks.chatapp.filter;

import com.tks.chatapp.entity.User;
import com.tks.chatapp.repository.UserRepository;
import com.tks.chatapp.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    
    private final JwtTokenUtil jwtTokenUtil;

    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFormToken((token));
            } catch (IllegalArgumentException e) {
                log.error("Unable to get jwt token");
            } catch (ExpiredJwtException e) {
                log.error("Jwt token is expired");
            }
        }
        getTokenAndValidate(username, request);

        filterChain.doFilter(request, response);

    }

    private void getTokenAndValidate(String username, HttpServletRequest request) {
        if (Objects.nonNull(username)) {
            Optional<String> loggedUsernameOtp = Optional.ofNullable(
                    SecurityContextHolder.getContext().getAuthentication()
            ).map(Authentication::getName);

            if (loggedUsernameOtp.isEmpty() || loggedUsernameOtp.get().equals(username)) {
                Optional<User> otp = userRepository.findByUsernameAndDeleted(username, false);
                if (otp.isPresent()) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            otp.get(),
                            null,
                            null
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
    }
}
