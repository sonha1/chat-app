package com.tks.chatapp.config;

import com.tks.chatapp.filter.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private OncePerRequestFilter jwtTokenFilter ;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint ;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizeRequests ->
                        authorizeRequests.requestMatchers(
                                "/v3/api-docs/**",
                                "/webjars/**",            // swagger-ui webjars
                                "/swagger-resources/**",  // swagger-ui resources
                                "/configuration/**",      // swagger configuration
                                "/*.html",
                                "/favicon.ico",
                                "/**/*.html",
                                "/**/*.css",
                                "/**/*.js",
                                "/test",
                                "/swagger-ui*/**",
                                "/auth/**",
                                "/static-data/**"
                        )).authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .exceptionHandling(exceptions ->exceptions.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
