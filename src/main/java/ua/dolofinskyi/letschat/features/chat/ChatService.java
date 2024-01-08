package ua.dolofinskyi.letschat.features.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.crud.CrudService;

import java.util.ArrayList;
import java.util.Iterator;
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
        List<Chat> list = new ArrayList<>();
        Iterator<Chat> iterator = chatRepository.findAll().iterator();
        iterator.forEachRemaining(list::add);
        return list;
    }
}
