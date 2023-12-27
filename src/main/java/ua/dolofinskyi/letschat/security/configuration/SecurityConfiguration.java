package ua.dolofinskyi.letschat.security.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.dolofinskyi.letschat.security.endpoint.EndpointService;
import ua.dolofinskyi.letschat.security.jwt.JwtAuthProvider;
import ua.dolofinskyi.letschat.security.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final EndpointService endpointService;
    private final JwtAuthProvider jwtAuthProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(new JwtFilter(endpointService, jwtAuthProvider), UsernamePasswordAuthenticationFilter.class);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.logout(LogoutConfigurer::permitAll);
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(request -> {
                    endpointService.getSecuredUrls().forEach(uri -> request.requestMatchers(uri).authenticated());
                    request.anyRequest().permitAll();
                }
        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
