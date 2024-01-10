package ua.dolofinskyi.letschat.features.chat;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Set;

@Data
@RedisHash
@Builder
public class GroupChat implements Chat {
    @Id
    private final String name;
    private final Set<String> users;
    private final Set<String> messages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupChat chat = (GroupChat) o;
        return name.equals(chat.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean isPrivate() {
        return false;
    }
}
