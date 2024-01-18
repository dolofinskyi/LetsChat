package ua.dolofinskyi.letschat.security.authetication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    public String token;
}
