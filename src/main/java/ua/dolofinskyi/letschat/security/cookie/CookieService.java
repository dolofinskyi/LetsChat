package ua.dolofinskyi.letschat.security.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
    @Getter
    @Value("${cookie.expiration}")
    private int expiration;

    public void setCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(expiration);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
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
