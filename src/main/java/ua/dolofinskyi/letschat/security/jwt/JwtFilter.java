package ua.dolofinskyi.letschat.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.security.authetication.AuthProvider;
import ua.dolofinskyi.letschat.security.cookie.CookieService;
import ua.dolofinskyi.letschat.security.filter.FilterService;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final AuthProvider authProvider;
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

        if (filterService.isUriSecured(request.getRequestURI()) &&
                !authProvider.isValidData(subject, token)) {
            filterService.redirect(request, response, filterChain, "/auth/login");
            return;
        }

        authProvider.authenticateUser(request, response, subject);
        filterChain.doFilter(request, response);
    }


}
