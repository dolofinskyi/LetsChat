package ua.dolofinskyi.letschat.security.action.login;

import lombok.Data;
import ua.dolofinskyi.letschat.security.action.ActionDetails;

@Data
public class LoginDetails implements ActionDetails {
    private String username;
    private String password;
}
