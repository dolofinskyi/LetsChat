package ua.dolofinskyi.letschat.features.message;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dolofinskyi.letschat.features.chat.Chat;
import ua.dolofinskyi.letschat.features.chat.ChatService;
import ua.dolofinskyi.letschat.features.crud.CrudService;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService implements CrudService<Message, String> {
    private final MessageRepository messageRepository;
    private final ChatService chatService;
    private final UserMapper userMapper;
    private final SimpMessagingTemplate template;
    private final MessageMapper messageMapper;

    @Override
    public Message add(Message entity) {
        return messageRepository.save(entity);
    }

    @Override
    public Message get(String id) {
        return messageRepository.findById(id).orElseThrow();
    }

    @Override
    public Message update(Message entity) {
        return messageRepository.save(entity);
    }

    @Override
    public void delete(Message entity) {
        messageRepository.delete(entity);
    }

    @Override
    public List<Message> listAll() {
        return messageRepository.findAll();
    }

    @Transactional
    public void sendMessage(MessageDto dto) {
        List<String> usernames = List.of(dto.getFrom(), dto.getTo());
        List<User> users = userMapper.usernamesToEntities(usernames);

        Chat chat = chatService.findChatByUsernames(usernames);

        Message message = add(
                Message.builder()
                        .from(dto.getFrom())
                        .to(dto.getTo())
                        .content(dto.getContent())
                        .chatId(chat.getId())
                        .build()
        );

        for (User user: users) {
            String destination = String.format("/user/%s/queue/messages", user.getSessionId());
            template.convertAndSend(destination, messageMapper.dtoToMessageSendResponse(dto));
        }
    }

    public List<Message> findMessagesByChatId(String chatId) {
        return listAll().stream()
                .filter(message -> message.getChatId().equals(chatId))
                .toList();
    }
}
