package ua.dolofinskyi.letschat.features.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.dolofinskyi.letschat.features.mapper.Mapper;

@Component
@RequiredArgsConstructor
public class ChatMapper implements Mapper<Chat, ChatDto> {

    @Override
    public Chat toEntity(ChatDto dto) {
        return Chat.builder()
                .users(dto.getUsers())
                .messages(dto.getMessages())
                .build();
    }

    @Override
    public ChatDto toDto(Chat entity) {
        return ChatDto.builder()
                .users(entity.getUsers())
                .messages(entity.getMessages())
                .build();
    }
}
