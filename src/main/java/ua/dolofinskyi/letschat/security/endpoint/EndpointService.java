package ua.dolofinskyi.letschat.security.endpoint;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EndpointService {
    private final List<String> SECURED_URLS = List.of("/app");

    public List<String> getSecuredUrls() {
        return List.copyOf(SECURED_URLS);
    }

    public boolean isUriSecured(String uri) {
        return SECURED_URLS.contains(uri);
    }
}
