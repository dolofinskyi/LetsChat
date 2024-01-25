package ua.dolofinskyi.letschat.features.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.dolofinskyi.letschat.features.mapper.Mapper;

@Component
@RequiredArgsConstructor
public class MessageMapper implements Mapper<Message, MessageDto> {

    @Override
    public Message toEntity(MessageDto dto) {
        return Message.builder()
                .from(dto.getFrom())
                .to(dto.getTo())
                .content(dto.getContent())
                .build();
    }

    @Override
    public MessageDto toDto(Message entity) {
        return MessageDto.builder()
                .from(entity.getFrom())
                .to(entity.getTo())
                .content(entity.getContent())
                .build();
    }
}
