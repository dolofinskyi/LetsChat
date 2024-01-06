package ua.dolofinskyi.letschat.security.endpoint;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EndpointService {
    private final List<String> SECURED_URL_PATTERNS = List.of("/app/**", "/api/**", "/ws");
    private final List<String> SECURED_URLS = List.of("/app", "/api", "/ws");

    public List<String> getSecuredUrlPatterns() {
        return List.copyOf(SECURED_URL_PATTERNS);
    }

    public boolean isUriSecured(String uri) {
        for (String u: SECURED_URLS) {
            if (uri.startsWith(u)) {
                return true;
            }
        }
        return false;
    }
}
