package ua.dolofinskyi.letschat.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.dolofinskyi.letschat.security.authetication.AuthenticationService;
import ua.dolofinskyi.letschat.security.authetication.CustomAuthenticationProvider;
import ua.dolofinskyi.letschat.security.cookie.CookieService;
import ua.dolofinskyi.letschat.security.endpoint.EndpointService;
import ua.dolofinskyi.letschat.security.jwt.JwtFilter;
import ua.dolofinskyi.letschat.security.jwt.JwtService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final EndpointService endpointService;
    private final CookieService cookieService;
    private final CustomAuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authenticationProvider);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.logout(form -> form.logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/login")
                .invalidateHttpSession(true)
                .deleteCookies("Subject", "Token")
                .permitAll());
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(
                request -> {
                    endpointService.getSecuredUrlPatterns().forEach(uri -> request.requestMatchers(uri).authenticated());
                    request.anyRequest().permitAll();
                }
        );
        return http.build();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(
                jwtService,
                authenticationService,
                endpointService,
                cookieService
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
