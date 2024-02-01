package ua.dolofinskyi.letschat.features.chat;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@Data
@RedisHash
@Builder
public class Chat {
    @Id
    private final String id;
    private final List<String> users;
    private final List<String> messages;

    public List<String> getUsers() {
        if (users == null) {
            return new ArrayList<>();
        }
        return users;
    }

    public List<String> getMessages() {
        if (messages == null) {
            return new ArrayList<>();
        }
        return messages;
    }
}
