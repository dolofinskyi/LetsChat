package ua.dolofinskyi.letschat.security.action.register;

import lombok.Data;
import ua.dolofinskyi.letschat.security.action.ActionDetails;

@Data
public class RegisterDetails implements ActionDetails {
    private String username;
    private String password;
    private String repeatPassword;
}
