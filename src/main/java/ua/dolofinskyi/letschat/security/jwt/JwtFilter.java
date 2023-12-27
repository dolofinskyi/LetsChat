package ua.dolofinskyi.letschat.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.dolofinskyi.letschat.security.endpoint.EndpointService;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    private final EndpointService endpointService;
    private final JwtAuthProvider jwtAuthProvider;

    public JwtFilter(EndpointService endpointService, JwtAuthProvider jwtAuthProvider) {
        this.endpointService = endpointService;
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
            if (endpointService.isUriSecured(request.getRequestURI())) {
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
