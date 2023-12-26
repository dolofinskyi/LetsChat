package ua.dolofinskyi.letschat.security.action.login;

import jakarta.servlet.http.HttpServletRequest;
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
    public AuthResponse action(HttpServletRequest request, LoginDetails details) {
        User user = process(details);
        String token = jwtUtil.generateToken(user.getUsername(), user.getSecret());
        authProvider.auth(request, details);
        return AuthResponse.builder().authorization(token).subject(user.getUsername()).build();
    }

    @Override
    public User process(LoginDetails details) {
        if (!userService.isUserExist(details.getUsername())) {
            //TODO throw UserNotFoundException
            throw new RuntimeException();
        }
        UserDetails user = userService.loadUserByUsername(details.getUsername());

        if (!passwordEncoder.matches(details.getPassword(), user.getPassword())) {
            //TODO throw InvalidPasswordException
            throw new RuntimeException();
        }
        return (User) user;
    }
}
