package ua.dolofinskyi.letschat.security.action;

import jakarta.servlet.http.HttpServletRequest;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.security.authorization.AuthResponse;

public interface ActionService<T extends ActionDetails> {
    AuthResponse action(HttpServletRequest request, T details);
    User process(T details);
}
