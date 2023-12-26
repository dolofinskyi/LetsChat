package ua.dolofinskyi.letschat.security.authorization;

import lombok.Data;

@Data
public class AuthResponse {
    private String authorization;
    private String subject;
}
