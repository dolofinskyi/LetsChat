package ua.dolofinskyi.letschat.security.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
    @Getter
    @Value("${cookie.expiration}")
    private int expiration;

    public Cookie createCookie(String name,
                               String value,
                               String path,
                               int expiration,
                               boolean httpOnly,
                               boolean isSecure) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        cookie.setMaxAge(expiration);
        cookie.setHttpOnly(httpOnly);
        cookie.setSecure(isSecure);
        return cookie;
    }

    public Cookie createDefaultCookie(String name, String value) {
        return createCookie(name, value, "/", expiration, true, true);
    }

    public String getCookieValue(HttpServletRequest request, String name) {
        for (Cookie cookie: request.getCookies()) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        throw new NullPointerException();
    }
}
