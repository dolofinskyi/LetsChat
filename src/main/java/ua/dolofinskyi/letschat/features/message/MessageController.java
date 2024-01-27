package ua.dolofinskyi.letschat.features.message;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ua.dolofinskyi.letschat.features.user.UserService;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final UserService userService;
    private final SimpMessagingTemplate template;

    @MessageMapping("/send")
    public void send(@Payload MessageDto messageDto) {
        String toSessionId = userService.findByUsername(messageDto.getTo()).getSessionId();
        template.convertAndSend(String.format("/user/%s/queue/messages", toSessionId), messageDto);
    }
}
