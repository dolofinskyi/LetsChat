package ua.dolofinskyi.letschat.features.message;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash
@Builder
public class Message {
    @Id
    private final String id;
    private final String sender;
    private final String receiver;
    private final Object content;
    private final MessageType type;
}
