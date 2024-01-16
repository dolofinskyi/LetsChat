package ua.dolofinskyi.letschat.security.login;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.security.authetication.AuthProvider;
import ua.dolofinskyi.letschat.security.authetication.AuthResponse;
import ua.dolofinskyi.letschat.security.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthProvider authProvider;

    public AuthResponse login(HttpServletResponse response, LoginDetails details) {
        if (!valid(details)) {
            return AuthResponse.builder().build();
        }
        User user = userService.findByUsername(details.getUsername());
        String token = jwtUtil.generateToken(user);
        authProvider.authenticate(user.getUsername());
        jwtUtil.setJwtCookies(response, user.getUsername(), token);
        return AuthResponse.builder().token(token).build();
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
