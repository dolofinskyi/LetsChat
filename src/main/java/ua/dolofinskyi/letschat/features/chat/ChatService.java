package ua.dolofinskyi.letschat.features.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.crud.CrudService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService implements CrudService<Chat, String> {
    private final ChatRepository chatRepository;

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

    private Chat createChat(List<String> usernames) {
        return add(
                Chat.builder().users(usernames).build()
        );
    }

    public List<String> findChatUsernamesByUsername(String username) {
        List<String> result = new ArrayList<>();

        for(Chat chat: listAll()) {
            if (chat.getUsers().contains(username)) {
                for (String user: chat.getUsers()) {
                    if (!user.equals(username)) {
                        result.add(user);
                    }
                }
            }
        }

        return result;
    }

    public Chat findChatByUsernames(List<String> usernames) {
        if (!isChatExist(usernames)) {
            return createChat(usernames);
        }

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
