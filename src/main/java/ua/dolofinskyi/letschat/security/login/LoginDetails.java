package ua.dolofinskyi.letschat.security.login;

import lombok.Data;
import ua.dolofinskyi.letschat.security.authetication.AuthenticationDetails;

@Data
public class LoginDetails implements AuthenticationDetails {
    private String username;
    private String password;
}
