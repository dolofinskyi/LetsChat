package ua.dolofinskyi.letschat.security.endpoint;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EndpointService {
    private final List<String> SECURED_URLS = List.of("/app");
    private final List<String> UNSECURED_URLS = List.of("/register", "/login");

    public List<String> getSecuredUrls() {
        return List.copyOf(SECURED_URLS);
    }

    public List<String> getUnsecuredUrls() {
        return List.copyOf(UNSECURED_URLS);
    }

    public boolean isUriSecured(String uri) {
        return SECURED_URLS.contains(uri);
    }

    public boolean isUriUnsecured(String uri) {
        return UNSECURED_URLS.contains(uri);
    }
}
