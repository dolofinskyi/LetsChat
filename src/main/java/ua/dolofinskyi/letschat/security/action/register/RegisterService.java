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
import ua.dolofinskyi.letschat.security.jwt.JwtUtil;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthProvider authProvider;

    public AuthResponse register(HttpServletRequest request, HttpServletResponse response, RegisterDetails details) {
        if (!valid(details)) {
            return AuthResponse.builder().build();
        }
        User user = userService.createUser(
                details.getUsername(),
                passwordEncoder.encode(details.getPassword()),
                jwtUtil.generateSecret()
        );
        String token = jwtUtil.generateToken(user);
        userService.add(user);
        authProvider.setAuthenticationCookies(response, user.getUsername(), token);
        authProvider.authenticate(request, details);
        return AuthResponse.builder().token(token).build();
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
