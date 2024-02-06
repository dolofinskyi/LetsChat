package ua.dolofinskyi.letschat.features.user;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public String redirectLogin() throws IOException {
        return "redirect:/auth/login?error";
    }

    @ExceptionHandler(UserFoundException.class)
    public String redirectRegister() throws IOException {
        return "redirect:/auth/register?error";
    }
}
