package ua.dolofinskyi.letschat.features.chat;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Set;

@Data
@RedisHash
@Builder
public class Chat {
    @Id
    private String id;
    private Set<String> users;
}
