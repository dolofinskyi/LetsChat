package ua.dolofinskyi.letschat.security.action.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.security.action.ActionService;
import ua.dolofinskyi.letschat.security.authorization.AuthProvider;
import ua.dolofinskyi.letschat.security.authorization.AuthResponse;
import ua.dolofinskyi.letschat.security.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
public class LoginService implements ActionService<LoginDetails> {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthProvider authProvider;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse doAction(HttpServletRequest request, HttpServletResponse response,
                               LoginDetails details) {
        if (!verify(details)) {
            return AuthResponse.builder().build();
        }
        User user = (User) userService.loadUserByUsername(details.getUsername());
        String token = jwtUtil.generateToken(user.getUsername(), user.getSecret());
        authProvider.auth(request, details);
        authProvider.setCookie(response, "Authorization", token);
        authProvider.setCookie(response, "Subject", user.getUsername());
        return AuthResponse.builder().authorization(token).build();
    }

    @Override
    public boolean verify(LoginDetails details) {
        if (!userService.isUserExist(details.getUsername())) {
            //TODO throw UserNotFoundException
            return false;
        }
        UserDetails user = userService.loadUserByUsername(details.getUsername());

        //TODO throw InvalidPasswordException
        return passwordEncoder.matches(details.getPassword(), user.getPassword());
    }
}
