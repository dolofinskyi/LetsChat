package ua.dolofinskyi.letschat.features.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.crud.CrudService;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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

    public Chat createChat(Set<String> users) {
        return add(
                Chat.builder()
                        .users(users)
                        .messages(Collections.emptySet())
                        .build()
        );
    }

    public Chat createChat() {
        return add(
                Chat.builder()
                        .users(Collections.emptySet())
                        .messages(Collections.emptySet())
                        .build()
        );
    }

    public Chat findChatByUsers(Set<String> users) {
        return listAll().stream()
                .filter(chat -> chat.getUsers().containsAll(users))
                .findFirst()
                .orElseThrow();
    }

    public boolean isChatExist(Set<String> users) {
        return listAll().stream()
                .anyMatch(chat -> chat.getUsers().containsAll(users));
    }
}
