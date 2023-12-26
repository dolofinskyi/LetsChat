package ua.dolofinskyi.letschat.security.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.dolofinskyi.letschat.features.user.User;

public interface ActionService<T extends ActionDetails> {
    void action(HttpServletRequest request, HttpServletResponse response, T details);
    User process(T details);
}
