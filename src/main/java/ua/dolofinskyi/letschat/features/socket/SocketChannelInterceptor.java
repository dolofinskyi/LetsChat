package ua.dolofinskyi.letschat.features.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocketChannelInterceptor implements ChannelInterceptor {
    private final SocketUserService socketUserService;

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        MessageHeaders headers = message.getHeaders();
        SimpMessageType command = (SimpMessageType) headers.get("simpMessageType");
        String sessionId = (String) headers.get("simpSessionId");
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) headers.get("simpUser");

        if (command == null || auth == null || auth.getName() == null || !auth.isAuthenticated()) {
            return;
        }

        switch (command) {
            case CONNECT -> socketUserService.connect(auth.getName(), sessionId);
            case DISCONNECT -> socketUserService.disconnect(auth.getName());
        }
    }


}
