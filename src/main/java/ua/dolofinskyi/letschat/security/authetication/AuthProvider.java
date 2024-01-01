package ua.dolofinskyi.letschat.security.authetication;

import com.sun.security.auth.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.security.cookie.CookieService;
import ua.dolofinskyi.letschat.security.jwt.JwtUtil;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CookieService cookieService;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        return check(username, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public AuthResponse authenticateUser(HttpServletRequest request, HttpServletResponse response,
                                         User user, AuthDetails details) {
        String token = jwtUtil.generateToken(user);
        setAuthenticationCookies(response, user.getUsername(), token);
        authenticate(request, details);
        return AuthResponse.builder().token(token).build();
    }

    private void setAuthenticationCookies(HttpServletResponse response, String subject, String token) {
        cookieService.setCookie(response, "Subject", subject);
        cookieService.setCookie(response, "Token", token);
    }

    private void authenticate(HttpServletRequest request, AuthDetails details) {
        UserPrincipal principal = new UserPrincipal(details.getUsername());
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(principal, details.getPassword());
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Authentication check(String username, String password) {
        UserDetails user = userService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        UserPrincipal principal = new UserPrincipal(user.getUsername());
        return new UsernamePasswordAuthenticationToken(principal, password, Collections.emptyList());
    }
}
