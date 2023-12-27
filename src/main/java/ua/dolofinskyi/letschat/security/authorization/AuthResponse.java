package ua.dolofinskyi.letschat.security.authorization;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    public String authorization;
}
