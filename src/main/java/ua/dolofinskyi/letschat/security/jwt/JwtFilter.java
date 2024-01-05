package ua.dolofinskyi.letschat.security.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.dolofinskyi.letschat.security.authetication.AuthProvider;
import ua.dolofinskyi.letschat.security.cookie.CookieService;
import ua.dolofinskyi.letschat.security.filter.FilterService;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final AuthProvider authProvider;
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

        try {
            String subject = cookieService.getCookieValue(request, "Subject");
            String token = cookieService.getCookieValue(request, "Token");
            jwtUtil.verifyToken(subject, token);
            authProvider.authenticateUser(request, response, subject);
        } catch (UsernameNotFoundException | JwtException e) {
            filterService.redirect(request, response, filterChain, "/auth/login");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
