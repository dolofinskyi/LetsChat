package ua.dolofinskyi.letschat.features.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import ua.dolofinskyi.letschat.features.socket.user.SocketUserService;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class SocketConfig implements WebSocketConfigurer {
    private final SocketUserService socketUserService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketConnectionHandler(), "/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    @Bean
    public SocketConnectionHandler socketConnectionHandler() {
        return new SocketConnectionHandler(socketUserService);
    }
}
