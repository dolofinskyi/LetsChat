package ua.dolofinskyi.letschat.features.message;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import ua.dolofinskyi.letschat.features.user.UserService;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final UserService userService;

    @MessageMapping("/send")
    public void send(@Header("simpSessionId") String sessionId, @Payload MessageSendRequest messageSendRequest) {
        String from = userService.findBySessionId(sessionId).getUsername();
        MessageDto dto = messageMapper.messageSendRequestToDto(from, messageSendRequest);
        messageService.sendMessage(dto);
    }
}
