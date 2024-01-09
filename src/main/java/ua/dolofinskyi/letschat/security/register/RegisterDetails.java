package ua.dolofinskyi.letschat.security.register;

import lombok.Data;
import ua.dolofinskyi.letschat.security.authetication.AuthDetails;

@Data
public class RegisterDetails implements AuthDetails {
    private String username;
    private String password;
    private String repeatPassword;
}
