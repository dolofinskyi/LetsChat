package ua.dolofinskyi.letschat.security.action.register;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.security.authetication.AuthProvider;
import ua.dolofinskyi.letschat.security.authetication.AuthResponse;
import ua.dolofinskyi.letschat.security.cookie.CookieService;
import ua.dolofinskyi.letschat.security.jwt.JwtUtil;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthProvider authProvider;
    private final CookieService cookieService;

    public AuthResponse register(HttpServletRequest request, HttpServletResponse response, RegisterDetails details) {
        if (!validate(details)) {
            return AuthResponse.builder().build();
        }
        authProvider.authenticate(request, details);
        User user = (User) userService.loadUserByUsername(details.getUsername());
        String token = jwtUtil.generateToken(user.getUsername(), user.getSecret());
        cookieService.setCookie(response, "Authorization", token);
        cookieService.setCookie(response, "Subject", user.getUsername());
        return AuthResponse.builder().authorization(token).build();
    }

    public boolean validate(RegisterDetails details) {
        if (userService.isUserExist(details.getUsername())) {
            //TODO throw UserFoundException
            return false;
        }
        if (!Objects.equals(details.getPassword(), details.getRepeatPassword())) {
            //TODO throw InvalidPasswordException
            return false;
        }
        User user = userService.createUser(
                details.getUsername(),
                passwordEncoder.encode(details.getPassword()),
                jwtUtil.generateSecret()
        );
        userService.add(user);
        return true;
    }
}
