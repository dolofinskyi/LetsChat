package ua.dolofinskyi.letschat.security.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.dolofinskyi.letschat.security.authetication.AuthenticationService;
import ua.dolofinskyi.letschat.security.cookie.CookieService;
import ua.dolofinskyi.letschat.security.filter.FilterService;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final FilterService filterService;
    private final CookieService cookieService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (!filterService.isUriSecured(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getCookies() == null) {
            filterService.redirect(request, response, filterChain, "/auth/login");
            return;
        }

        String subject = cookieService.getCookieValue(request, "Subject");
        String token = cookieService.getCookieValue(request, "Token");

        if (subject == null || token == null) {
            filterService.redirect(request, response, filterChain, "/auth/login");
            return;
        }

        try {
            jwtService.verifyToken(subject, token);
            authenticationService.authenticate(subject);
        } catch (UsernameNotFoundException | JwtException e) {
            filterService.redirect(request, response, filterChain, "/auth/login");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
