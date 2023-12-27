package ua.dolofinskyi.letschat.security.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.dolofinskyi.letschat.security.authetication.AuthResponse;

public interface ActionService<T extends ActionDetails> {
    AuthResponse doAction(HttpServletRequest request, HttpServletResponse response, T details);
    boolean validate(T details);
}
