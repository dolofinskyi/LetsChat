package ua.dolofinskyi.letschat.features.chat.personal;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import ua.dolofinskyi.letschat.features.chat.Chat;
import ua.dolofinskyi.letschat.features.chat.ChatType;

import java.util.Set;

@Data
@RedisHash
@Builder
public class PersonalChat implements Chat {
    @Id
    private final String id;
    private final Set<String> users;
    private final Set<String> messages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalChat chat = (PersonalChat) o;
        return id.equals(chat.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public ChatType getType() {
        return ChatType.PERSONAL;
    }
}
