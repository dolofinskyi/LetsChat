package ua.dolofinskyi.letschat.security.authetication;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.dolofinskyi.letschat.security.login.InvalidLoginCredentialsException;
import ua.dolofinskyi.letschat.security.register.InvalidRegisterCredentialsException;

@ControllerAdvice
public class AuthenticationExceptionHandler {

    @ExceptionHandler(InvalidLoginCredentialsException.class)
    public String redirectInvalidLoginCredentials() {
        return "redirect:/auth/login?error";
    }

    @ExceptionHandler(InvalidRegisterCredentialsException.class)
    public String redirectInvalidRegisterCredentials() {
        return "redirect:/auth/register?error";
    }
}
