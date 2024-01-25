package ua.dolofinskyi.letschat.features.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDto {
    private final String sender;
    private final String receiver;
    private final Object content;
}
