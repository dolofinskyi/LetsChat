package ua.dolofinskyi.letschat.features.socket.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SocketUserController {
    private final SocketUserService socketUserService;

}
