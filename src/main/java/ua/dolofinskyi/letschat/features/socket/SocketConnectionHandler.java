package ua.dolofinskyi.letschat.features.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.security.Principal;

@RequiredArgsConstructor
public class SocketConnectionHandler extends AbstractWebSocketHandler {
    private final SocketUserService socketUserService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (session.getPrincipal() == null) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }
        Principal principal = session.getPrincipal();

        try {
            socketUserService.connect(principal.getName(), session.getId());
        } catch (UsernameNotFoundException e) {
            session.close(CloseStatus.BAD_DATA);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        if (closeStatus == CloseStatus.BAD_DATA || session.getPrincipal() == null) {
            session.close();
            return;
        }
        Principal principal = session.getPrincipal();

        try {
            socketUserService.disconnect(principal.getName());
        } catch (UsernameNotFoundException e) {
            session.close();
        }
    }
}
