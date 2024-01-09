package ua.dolofinskyi.letschat.security.authetication;

import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.security.context.SecurityContextService;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AuthProvider {
    private final SecurityContextService securityContextService;
    private final UserService userService;

    public void authenticate(String username) {
        authenticate(userService.findByUsername(username));
    }

    public void authenticate(User user) {
        Authentication authentication = getAuthentication(user.getUsername());
        securityContextService.setAuthentication(authentication);
    }

    public Authentication getAuthentication(String username) {
        return new UsernamePasswordAuthenticationToken(
                new UserPrincipal(username),
                null,
                Collections.emptyList()
        );
    }
}
