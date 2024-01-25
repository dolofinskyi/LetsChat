package ua.dolofinskyi.letschat.security.login;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.security.authetication.AuthenticationService;
import ua.dolofinskyi.letschat.security.authetication.AuthenticationResponse;
import ua.dolofinskyi.letschat.security.jwt.JwtService;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationResponse login(HttpServletResponse response, LoginDetails details) {
        if (!valid(details)) {
            return AuthenticationResponse.builder().build();
        }
        authenticationService.authenticate(details.getUsername(), details.getPassword());
        User user = userService.findByUsername(details.getUsername());
        String token = jwtService.generateToken(user);
        jwtService.setJwtCookies(response, user.getUsername(), token);
        return AuthenticationResponse.builder().token(token).build();
    }

    public boolean valid(LoginDetails details) {
        return userService.isUserExist(details.getUsername());
    }
}
