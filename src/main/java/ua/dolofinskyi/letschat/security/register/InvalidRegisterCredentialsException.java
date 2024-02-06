package ua.dolofinskyi.letschat.security.register;

import org.springframework.security.authentication.BadCredentialsException;

public class InvalidRegisterCredentialsException extends BadCredentialsException {

    public InvalidRegisterCredentialsException() {
        super("Invalid credentials.");
    }
}
