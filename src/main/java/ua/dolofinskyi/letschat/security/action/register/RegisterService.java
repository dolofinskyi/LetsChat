package ua.dolofinskyi.letschat.security.action.register;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.security.action.ActionService;
import ua.dolofinskyi.letschat.security.authorization.AuthProvider;
import ua.dolofinskyi.letschat.security.authorization.AuthResponse;
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
    public AuthResponse action(HttpServletRequest request, RegisterDetails details) {
        AuthResponse result = new AuthResponse();
        User user = process(details);
        String token = jwtUtil.generateToken(user.getUsername(), user.getSecret());
        result.setAuthorization(token);
        result.setSubject(user.getUsername());
        authProvider.auth(request, details);
        return result;
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
