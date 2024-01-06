package ua.dolofinskyi.letschat.features.socket;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import ua.dolofinskyi.letschat.security.context.SecurityContextService;

@Controller
@RequiredArgsConstructor
public class SocketUserController {
    private final SocketUserService socketUserService;
    private Logger logger = LoggerFactory.getLogger(SocketUserController.class);

    @MessageMapping("/user.connect")
    public void connect(@Payload SocketUser user) {
        socketUserService.connect(user);
        logger.info("connected! " + user.getSessionId());
    }

    @MessageMapping("/user.disconnect")
    public void disconnect(@Payload SocketUser user) {
        socketUserService.disconnect(user);
        logger.info("disconnected! " + user.getSessionId());
    }
}
