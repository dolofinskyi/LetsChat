package ua.dolofinskyi.letschat.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.dolofinskyi.letschat.security.cookie.CookieService;
import ua.dolofinskyi.letschat.security.filter.FilterService;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtAuthProvider jwtAuthProvider;
    private final FilterService filterService;
    private final CookieService cookieService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getCookies() == null) {
            filterService.redirect(request, response, filterChain, "/auth/login");
            return;
        }
        String subject = cookieService.getCookie(request.getCookies(), "Subject");
        String token = cookieService.getCookie(request.getCookies(), "Token");

        if (!jwtAuthProvider.isValidData(subject, token)) {
            filterService.redirect(request, response, filterChain, "/auth/login");
            return;
        }

        jwtAuthProvider.authenticate(request, subject, token);
        filterChain.doFilter(request, response);
    }


}
