package ua.dolofinskyi.letschat.features.message.text;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import ua.dolofinskyi.letschat.features.message.Message;
import ua.dolofinskyi.letschat.features.message.MessageType;

@Data
@RedisHash
@Builder
public class TextMessage implements Message {
    @Id
    private final String id;
    private final String sender;
    private final String receiver;
    private final String content;

    @Override
    public MessageType getType() {
        return MessageType.TEXT;
    }
}
