package ua.dolofinskyi.letschat.features.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.features.user.UserStatus;
import ua.dolofinskyi.letschat.security.context.SecurityContextService;

@Service
@RequiredArgsConstructor
public class SocketUserService {
    private final SecurityContextService contextService;
    private final UserService userService;


    public void connect(SocketUser socketUser) {
        String username = contextService.getPrincipalName();
        User user = (User) userService.loadUserByUsername(username);
        user.setStatus(UserStatus.ONLINE);
        userService.update(user);
    }
    public void disconnect(SocketUser socketUser) {
        String username = contextService.getPrincipalName();
        User user = (User) userService.loadUserByUsername(username);
        user.setStatus(UserStatus.OFFLINE);
        userService.update(user);
    }
}
