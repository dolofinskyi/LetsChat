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
                .sender(dto.getSender())
                .receiver(dto.getReceiver())
                .content(dto.getContent())
                .build();
    }

    @Override
    public MessageDto toDto(Message entity) {
        return MessageDto.builder()
                .sender(entity.getSender())
                .receiver(entity.getReceiver())
                .content(entity.getContent())
                .build();
    }
}
