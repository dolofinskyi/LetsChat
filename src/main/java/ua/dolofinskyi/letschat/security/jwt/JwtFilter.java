package ua.dolofinskyi.letschat.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
        String subject = request.getHeader("Subject");
        String token = getAuthTokenFromHeader(request);
        boolean isUriSecured = endpointHolder.isUriSecured(request.getRequestURI());

        if (!jwtAuthProvider.isDataValid(subject, token)) {
            if (isUriSecured) {
                response.sendRedirect("/auth/login");
            } else {
                filterChain.doFilter(request, response);
            }
            return;
        }

        jwtAuthProvider.auth(request, subject, token);
        filterChain.doFilter(request, response);
    }

    private String getAuthTokenFromHeader(HttpServletRequest request) {;
        String token = request.getHeader("Authorization");
        if (token != null) {
            return token.substring(7);
        }
        return null;
    }
}
