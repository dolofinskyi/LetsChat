package ua.dolofinskyi.letschat.features.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String username;
    private UserStatus status;
}
