package ua.dolofinskyi.letschat.security.register;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.security.authetication.AuthenticationService;
import ua.dolofinskyi.letschat.security.authetication.AuthenticationResponse;
import ua.dolofinskyi.letschat.security.jwt.JwtService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationResponse register(HttpServletResponse response, RegisterDetails details) {
        if (!valid(details)) {
            return AuthenticationResponse.builder().build();
        }

        User user = userService.createUser(
                details.getUsername(),
                passwordEncoder.encode(details.getPassword()),
                jwtService.generateSecret()
        );

        String token = jwtService.generateToken(user);
        authenticationService.authenticate(user.getUsername());
        jwtService.setJwtCookies(response, user.getUsername(), token);
        return AuthenticationResponse.builder().token(token).build();
    }

    public boolean valid(RegisterDetails details) {
        if (userService.isUserExist(details.getUsername())) {
            //TODO throw UserFoundException
            return false;
        }
        //TODO throw InvalidPasswordException
        return Objects.equals(details.getPassword(), details.getRepeatPassword());
    }
}
