package ua.dolofinskyi.letschat.features.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.dolofinskyi.letschat.features.mapper.Mapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserDto> {
    private final UserService userService;

    @Override
    public User toEntity(UserDto dto) {
        return userService.findByUsername(dto.getUsername());
    }

    @Override
    public UserDto toDto(User entity) {
        return UserDto.builder()
                .username(entity.getUsername())
                .status(entity.getStatus())
                .build();
    }

    public List<UserDto> usernamesToDtos(List<String> usernames) {
        return toDtos(usernames.stream().map(userService::findByUsername).toList());
    }
}
