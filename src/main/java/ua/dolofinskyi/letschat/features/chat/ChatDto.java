package ua.dolofinskyi.letschat.features.chat;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatDto {
    private List<String> users;
    private List<String> messages;
}
