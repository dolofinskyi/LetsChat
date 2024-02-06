package ua.dolofinskyi.letschat.security.login;

import org.springframework.security.authentication.BadCredentialsException;

public class InvalidLoginCredentialsException extends BadCredentialsException {

    public InvalidLoginCredentialsException() {
        super("Invalid credentials.");
    }
}
