package ua.dolofinskyi.letschat.security.authetication;

import com.sun.security.auth.UserPrincipal;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.security.context.SecurityContextService;
import ua.dolofinskyi.letschat.security.cookie.CookieService;
import ua.dolofinskyi.letschat.security.jwt.JwtUtil;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AuthProvider {
    private final SecurityContextService securityContextService;
    private final UserService userService;
    private final CookieService cookieService;
    private final JwtUtil jwtUtil;

    public AuthResponse authenticate(HttpServletResponse response, String subject) throws UsernameNotFoundException {
        return authenticate(response, (User) userService.loadUserByUsername(subject));
    }

    public AuthResponse authenticate(HttpServletResponse response, User user) {
        String token = jwtUtil.generateToken(user);
        setAuthenticationCookies(response, user.getUsername(), token);
        Authentication authentication = getAuthentication(user.getUsername());
        securityContextService.setAuthentication(authentication);
        return AuthResponse.builder().token(token).build();
    }

    public void setAuthenticationCookies(HttpServletResponse response, String subject, String token) {
        cookieService.setCookie(response, "Subject", subject, true);
        cookieService.setCookie(response, "Token", token, true);
    }

    public Authentication getAuthentication(String subject) {
        return new UsernamePasswordAuthenticationToken(
                new UserPrincipal(subject),
                null,
                Collections.emptyList()
        );
    }
}
