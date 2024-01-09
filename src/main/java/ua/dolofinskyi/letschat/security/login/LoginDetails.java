package ua.dolofinskyi.letschat.security.login;

import lombok.Data;
import ua.dolofinskyi.letschat.security.authetication.AuthDetails;

@Data
public class LoginDetails implements AuthDetails {
    private String username;
    private String password;
}
