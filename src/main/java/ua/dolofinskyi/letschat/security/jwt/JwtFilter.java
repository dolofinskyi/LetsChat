package ua.dolofinskyi.letschat.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.dolofinskyi.letschat.security.configuration.EndpointHolder;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    private final EndpointHolder endpointHolder;
    private final JwtAuthProvider jwtAuthProvider;

    public JwtFilter(EndpointHolder endpointHolder, JwtAuthProvider jwtAuthProvider) {
        this.endpointHolder = endpointHolder;
        this.jwtAuthProvider = jwtAuthProvider;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getCookies() == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String subject = jwtAuthProvider.getCookie(request, "Subject");
        String token = jwtAuthProvider.getCookie(request, "Authorization");
        if (!jwtAuthProvider.isValidData(subject, token)) {
            if (endpointHolder.isUriSecured(request.getRequestURI())) {
                response.sendRedirect("/auth/login");
            } else {
                filterChain.doFilter(request, response);
            }
            return;
        }

        jwtAuthProvider.auth(request, subject, token);
        filterChain.doFilter(request, response);
    }


}
