package ua.dolofinskyi.letschat.security.register;

import lombok.Data;
import ua.dolofinskyi.letschat.security.authetication.AuthenticationDetails;

@Data
public class RegisterDetails implements AuthenticationDetails {
    private String username;
    private String password;
    private String repeatPassword;
}
