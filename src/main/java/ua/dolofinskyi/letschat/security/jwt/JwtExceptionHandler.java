package ua.dolofinskyi.letschat.security.jwt;

import io.jsonwebtoken.JwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class JwtExceptionHandler {

    @ExceptionHandler(JwtException.class)
    public String redirectJwt() throws IOException {
        return "redirect:/auth/login";
    }
}
