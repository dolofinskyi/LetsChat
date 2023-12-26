package ua.dolofinskyi.letschat.security.action.register;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.security.action.ActionService;
import ua.dolofinskyi.letschat.security.authorization.AuthProvider;
import ua.dolofinskyi.letschat.security.jwt.JwtUtil;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegisterService implements ActionService<RegisterDetails> {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthProvider authProvider;

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response, RegisterDetails details) {
        User user = process(details);
        String token = jwtUtil.generateToken(user.getUsername(), user.getSecret());
        authProvider.auth(request, details);
        authProvider.setCookie(response, "Authorization", token);
        authProvider.setCookie(response, "Subject", user.getUsername());
    }

    @Override
    public User process(RegisterDetails details) {
        if (userService.isUserExist(details.getUsername())) {
            //TODO throw UserFoundException
            throw new RuntimeException();
        }
        if (!Objects.equals(details.getPassword(), details.getRepeatPassword())) {
            //TODO throw InvalidPasswordException
            throw new RuntimeException();
        }
        User user = userService.createUser(
                details.getUsername(),
                passwordEncoder.encode(details.getPassword()),
                jwtUtil.generateSecret()
        );
        return userService.add(user);
    }
}
