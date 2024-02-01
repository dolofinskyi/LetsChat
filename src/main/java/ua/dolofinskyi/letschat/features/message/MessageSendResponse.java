package ua.dolofinskyi.letschat.features.message;

import lombok.Builder;

@Builder
public class MessageSendResponse {
    private String from;
    private String to;
    private String content;
}
