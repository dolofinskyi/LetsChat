package ua.dolofinskyi.letschat.features.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SocketUserController {
    private final SocketUserService socketUserService;

    @MessageMapping("/user.connect")
    public void connect(@Header("simpSessionId") String sessionId) {

    }

    @MessageMapping("/user.disconnect")
    public void disconnect(@Header("simpSessionId") String sessionId) {

    }
}
