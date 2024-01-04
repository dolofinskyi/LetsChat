package ua.dolofinskyi.letschat.security.authetication;

import com.sun.security.auth.UserPrincipal;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.security.cookie.CookieService;
import ua.dolofinskyi.letschat.security.jwt.JwtUtil;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AuthProvider {
    private final UserService userService;
    private final CookieService cookieService;
    private final JwtUtil jwtUtil;

    public AuthResponse authenticateUser(HttpServletRequest request, HttpServletResponse response,
                                         String username) {
        return authenticateUser(request, response, (User) userService.loadUserByUsername(username));
    }

    public AuthResponse authenticateUser(HttpServletRequest request, HttpServletResponse response,
                                         User user) {
        String token = jwtUtil.generateToken(user);
        setAuthenticationCookies(response, user.getUsername(), token);
        authenticate(request, user.getUsername());
        return AuthResponse.builder().token(token).build();
    }

    private void setAuthenticationCookies(HttpServletResponse response, String subject, String token) {
        cookieService.setCookie(response, "Subject", subject);
        cookieService.setCookie(response, "Token", token);
    }

    private void authenticate(HttpServletRequest request, String subject) {
        UserPrincipal principal = new UserPrincipal(subject);
        UsernamePasswordAuthenticationToken user =
                new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());
        user.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(user);
    }

    public boolean verifyUser(String subject, String token)
            throws UsernameNotFoundException, JwtException {
        return jwtUtil.verifyToken(subject, token);
    }
}
