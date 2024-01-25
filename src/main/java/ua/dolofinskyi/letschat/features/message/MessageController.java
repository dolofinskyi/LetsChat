package ua.dolofinskyi.letschat.features.message;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;;

@Controller
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @MessageMapping("/send")
    public void send() {

    }
}
