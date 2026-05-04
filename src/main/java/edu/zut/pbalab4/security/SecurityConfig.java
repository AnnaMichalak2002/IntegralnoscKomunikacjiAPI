package edu.zut.pbalab4.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatchers;

@Configuration
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain basicAuthFilterChain(HttpSecurity http,
                                                    RestAuthenticationEntryPoint authenticationEntryPoint) throws Exception {

        RequestMatcher basicEndpoints = RequestMatchers.anyOf(
                PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/users"),
                PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/users"),
                PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/users/{id}")
        );

        http
                .securityMatcher(basicEndpoints)
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .httpBasic(basic -> basic.authenticationEntryPoint(authenticationEntryPoint));

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain bearerAuthFilterChain(HttpSecurity http,
                                                     RestAuthenticationEntryPoint authenticationEntryPoint) throws Exception {

        RequestMatcher bearerEndpoints = RequestMatchers.anyOf(
                PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.PUT, "/users/{id}"),
                PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.DELETE, "/users/{id}")
        );

        http
                .securityMatcher(bearerEndpoints)
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> {})
                        .authenticationEntryPoint(authenticationEntryPoint)
                );

        return http.build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain fallbackFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("ma59740")
                .password("{noop}123456")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}