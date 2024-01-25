package ua.dolofinskyi.letschat.security;

import com.sun.security.auth.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService {

    public UserPrincipal getUserPrincipal() {
        return (UserPrincipal) getAuthentication().getPrincipal();
    }

    public Authentication getAuthentication() {
        return getContext().getAuthentication();
    }

    public void setAuthentication(Authentication authentication) {
        getContext().setAuthentication(authentication);
    }

    public SecurityContext getContext() {
        return SecurityContextHolder.getContext();
    }
}
