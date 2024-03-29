package ua.dolofinskyi.letschat.features.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageSendRequest {
    private String to;
    private String content;
}
