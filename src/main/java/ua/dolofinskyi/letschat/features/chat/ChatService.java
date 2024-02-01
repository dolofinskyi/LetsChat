package ua.dolofinskyi.letschat.features.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dolofinskyi.letschat.features.crud.CrudService;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserMapper;
import ua.dolofinskyi.letschat.features.user.UserService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService implements CrudService<Chat, String> {
    private final ChatRepository chatRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public Chat add(Chat entity) {
        return chatRepository.save(entity);
    }

    @Override
    public Chat get(String id) {
        return chatRepository.findById(id).orElseThrow();
    }

    @Override
    public Chat update(Chat entity) {
        return chatRepository.save(entity);
    }

    @Override
    public void delete(Chat entity) {
        chatRepository.delete(entity);
    }

    @Override
    public List<Chat> listAll() {
        return chatRepository.findAll();
    }

    @Transactional
    public Chat createChat(List<String> usernames) {
        Chat chat = add(
                Chat.builder()
                        .users(usernames)
                        .messages(Collections.emptyList())
                        .build()
        );

        for (User user: userMapper.usernamesToEntities(usernames)) {
            user.getChats().addAll(
                    usernames.stream()
                    .filter(username -> !username.equals(user.getUsername()))
                    .toList()
            );
            userService.update(user);
        }

        return chat;
    }

    public Chat findChatByUsers(List<String> usernames) {
        return listAll().stream()
                .filter(chat -> new HashSet<>(chat.getUsers()).containsAll(usernames))
                .findFirst()
                .orElseThrow();
    }

    public boolean isChatExist(List<String> usernames) {
        return listAll().stream()
                .anyMatch(chat -> new HashSet<>(chat.getUsers()).containsAll(usernames));
    }
}
