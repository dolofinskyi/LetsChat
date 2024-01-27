package ua.dolofinskyi.letschat.features.message;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @SubscribeMapping

    @MessageMapping("/app/hello")
    public void send(MessageDto messageDto) {
        System.out.println(messageDto);
    }
}
