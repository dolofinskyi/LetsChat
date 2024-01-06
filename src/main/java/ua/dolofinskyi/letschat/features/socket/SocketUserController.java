package ua.dolofinskyi.letschat.features.socket;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SocketUserController {
    private Logger logger = LoggerFactory.getLogger(SocketUserController.class);

    @MessageMapping("/user.connect")
    public void connect(@Payload SocketUser user) {
        logger.info("connected! " + user.getSessionId());
    }

    @MessageMapping("/user.disconnect")
    public void disconnect(@Payload SocketUser user) {
        logger.info("disconnected! " + user.getSessionId());
    }
}
