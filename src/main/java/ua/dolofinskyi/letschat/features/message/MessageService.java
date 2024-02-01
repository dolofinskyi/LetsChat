package ua.dolofinskyi.letschat.features.message;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dolofinskyi.letschat.features.chat.Chat;
import ua.dolofinskyi.letschat.features.chat.ChatService;
import ua.dolofinskyi.letschat.features.crud.CrudService;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService implements CrudService<Message, String> {
    private final MessageRepository messageRepository;
    private final ChatService chatService;
    private final UserService userService;
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
    public void sendMessage(String from, MessageSend messageSend) {
        List<String> usernames = List.of(from, messageSend.getTo());
        User toUser = userService.findByUsername(messageSend.getTo());

        Chat chat = chatService.isChatExist(usernames) ?
                chatService.findChatByUsers(usernames) :
                chatService.createChat(usernames);

        Message message = add(messageMapper.messageSendRequestToEntity(from, messageSend));
        String destination = String.format("/user/%s/queue/messages", toUser.getSessionId());

        chat.getMessages().add(message.getId());
        chatService.update(chat);

        template.convertAndSend(destination, messageMapper.toDto(message));
    }
}
