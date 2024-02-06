package ua.dolofinskyi.letschat.security.register;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserFoundException;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.security.authetication.AuthenticationService;
import ua.dolofinskyi.letschat.security.jwt.JwtService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public void register(HttpServletResponse response, RegisterDetails details)
            throws UserFoundException {
        validate(details);

        User user = userService.createUser(
                details.getUsername(),
                passwordEncoder.encode(details.getPassword()),
                jwtService.generateSecret()
        );

        String token = jwtService.generateToken(user);
        authenticationService.authenticate(user.getUsername());
        jwtService.setJwtCookies(response, user.getUsername(), token);
    }

    public void validate(RegisterDetails details) throws UserFoundException {
        if (!Objects.equals(details.getPassword(), details.getRepeatPassword())) {
            throw new InvalidRegisterCredentialsException();
        }

        if (userService.isUserExist(details.getUsername())) {
            throw new UserFoundException();
        }
    }
}
