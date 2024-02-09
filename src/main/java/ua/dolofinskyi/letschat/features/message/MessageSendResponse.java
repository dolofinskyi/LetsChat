package ua.dolofinskyi.letschat.features.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageSendResponse {
    private String from;
    private String to;
    private String content;
}
