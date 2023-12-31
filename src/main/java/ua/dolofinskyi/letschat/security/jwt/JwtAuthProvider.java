package ua.dolofinskyi.letschat.security.jwt;

import com.sun.security.auth.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthProvider implements AuthenticationProvider {
    private final JwtUtil jwtUtil;

    public void authenticate(HttpServletRequest request, String subject, String token) {
        UserPrincipal principal = new UserPrincipal(subject);
        UsernamePasswordAuthenticationToken user =
                new UsernamePasswordAuthenticationToken(principal, token);
        user.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticate(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String subject = authentication.getName();
        UserPrincipal principal = new UserPrincipal(subject);
        return new UsernamePasswordAuthenticationToken(principal, null,
                Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public boolean isValidData(String subject, String token) {
        return subject != null && token != null && jwtUtil.verifyToken(subject, token);
    }
}
