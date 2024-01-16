package ua.dolofinskyi.letschat.security.authetication;

import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ua.dolofinskyi.letschat.security.context.SecurityContextService;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AuthProvider {
    private final SecurityContextService securityContextService;

    public void authenticate(String username) {
        Authentication authentication = getAuthentication(username);
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
