package ua.dolofinskyi.letschat.features.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDto {
    private final String from;
    private final String to;
    private final String content;
}
