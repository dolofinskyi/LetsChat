package ua.dolofinskyi.letschat.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.dolofinskyi.letschat.security.authetication.AuthenticationService;
import ua.dolofinskyi.letschat.security.cookie.CookieService;
import ua.dolofinskyi.letschat.security.endpoint.EndpointService;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final EndpointService endpointService;
    private final CookieService cookieService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (!endpointService.isUriSecured(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getCookies() == null) {
            response.sendRedirect("/auth/login");
            return;
        }

        String subject = cookieService.getCookieValue(request, "Subject");
        String token = cookieService.getCookieValue(request, "Token");

        if (subject == null || token == null) {
            response.sendRedirect("/auth/login");
            return;
        }

        jwtService.verifyToken(subject, token);
        authenticationService.authenticate(subject);

        filterChain.doFilter(request, response);
    }
}
