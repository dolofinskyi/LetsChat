package ua.dolofinskyi.letschat.security.authetication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ua.dolofinskyi.letschat.security.SecurityContextService;

@Component
@RequiredArgsConstructor
public class AuthenticationService {
    private final SecurityContextService securityContextService;
    private final CustomAuthenticationProvider authenticationProvider;

    public void authenticate(String username) {
        Authentication authentication = authenticationProvider.getAuthentication(username, null);
        securityContextService.setAuthentication(authentication);
    }

    public void authenticate(String username, String password) {
        Authentication authentication = authenticationProvider.authenticate(
                authenticationProvider.getAuthentication(username, password)
        );
        securityContextService.setAuthentication(authentication);
    }
}
