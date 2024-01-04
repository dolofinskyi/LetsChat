package ua.dolofinskyi.letschat.security.endpoint;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EndpointService {
    private final List<String> SECURED_URL_PATTERNS = List.of("/api/**", "/app/**");
    private final List<String> SECURED_URLS = List.of("/app");

    public List<String> getSecuredUrlPatterns() {
        return List.copyOf(SECURED_URL_PATTERNS);
    }

    public boolean isUriSecured(String uri) {
        return SECURED_URLS.contains(uri);
    }
}
