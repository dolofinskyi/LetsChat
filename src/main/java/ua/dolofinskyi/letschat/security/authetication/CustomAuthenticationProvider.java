package ua.dolofinskyi.letschat.security.authetication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.security.login.InvalidLoginCredentialsException;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        String encodedPassword = userService.findByUsername(username).getPassword();

        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new InvalidLoginCredentialsException();
        }

        return getAuthentication(username, null);
    }

    public Authentication getAuthentication(String username, String password) {
        return new UsernamePasswordAuthenticationToken(
                username, password, Collections.emptyList()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
