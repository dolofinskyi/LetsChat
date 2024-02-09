package ua.dolofinskyi.letschat.features.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.dolofinskyi.letschat.features.chat.Chat;
import ua.dolofinskyi.letschat.features.chat.ChatService;
import ua.dolofinskyi.letschat.features.message.MessageDto;
import ua.dolofinskyi.letschat.features.message.MessageMapper;
import ua.dolofinskyi.letschat.features.message.MessageService;
import ua.dolofinskyi.letschat.security.SecurityContextService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ChatService chatService;
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final UserMapper userMapper;
    private final SecurityContextService contextService;

    @GetMapping("/chats")
    public List<UserDto> chats() {
        String username = contextService.getUsername();
        return userMapper.usernamesToDtos(chatService.findChatsByUsername(username));
    }

    @GetMapping("/chat")
    public List<MessageDto> messages(@RequestParam("username") String to) {
        String from = contextService.getUsername();
        List<String> usernames = List.of(from, to);
        Chat chat = chatService.findChatByUsernames(usernames);
        return messageMapper.toDtos(messageService.findMessagesInChat(chat.getId()));
    }

    @GetMapping("/list")
    public List<User> list() {
        return userService.listAll();
    }

    @GetMapping("/search")
    public List<UserDto> search(@RequestParam String prefix) {
        return userMapper.toDtos(userService.findUsersByPrefix(prefix, contextService.getUsername()));
    }
}
