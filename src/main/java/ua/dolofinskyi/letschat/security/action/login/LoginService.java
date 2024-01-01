package ua.dolofinskyi.letschat.security.action.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.security.authetication.AuthProvider;
import ua.dolofinskyi.letschat.security.authetication.AuthResponse;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthProvider authProvider;

    public AuthResponse login(HttpServletRequest request, HttpServletResponse response,
                              LoginDetails details) {
        if (!valid(details)) {
            return AuthResponse.builder().build();
        }
        User user = (User) userService.loadUserByUsername(details.getUsername());
        return authProvider.authenticateUser(request, response, user, details);
    }

    public boolean valid(LoginDetails details) {
        if (!userService.isUserExist(details.getUsername())) {
            //TODO throw UserNotFoundException
            return false;
        }
        String encodedPassword = userService.loadUserByUsername(details.getUsername()).getPassword();
        //TODO throw InvalidPasswordException
        return passwordEncoder.matches(details.getPassword(), encodedPassword);
    }
}
