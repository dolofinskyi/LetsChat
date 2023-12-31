package ua.dolofinskyi.letschat.security.authetication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    public String token;
}
