package ua.dolofinskyi.letschat.features.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.features.crud.CrudService;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService implements CrudService<Message, String> {
    private final MessageRepository messageRepository;

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
}
