package ua.dolofinskyi.letschat.features.chat;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Data
@RedisHash
@Builder
public class Chat {
    @Id
    private final String id;
    private List<String> users;
}
