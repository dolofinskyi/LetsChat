package ua.dolofinskyi.letschat.features.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.features.user.UserStatus;

@Service
@RequiredArgsConstructor
public class SocketUserService {
    private final UserService userService;

    public void connect(String username, String sessionId) {
        User user = userService.findByUsername(username);
        user.setSessionId(sessionId);
        user.setStatus(UserStatus.ONLINE);
        userService.update(user);
    }
    public void disconnect(String username) {
        User user = userService.findByUsername(username);
        user.setStatus(UserStatus.OFFLINE);
        userService.update(user);
    }
}
