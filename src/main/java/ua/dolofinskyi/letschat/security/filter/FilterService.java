package ua.dolofinskyi.letschat.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.letschat.security.endpoint.EndpointService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FilterService {
    private final EndpointService endpointService;

    public void redirect(HttpServletRequest request, HttpServletResponse response,
                            FilterChain chain, String url) throws ServletException, IOException {
        if (endpointService.isUriSecured(request.getRequestURI())) {
            response.sendRedirect(url);
        } else {
            chain.doFilter(request, response);
        }
    }

    public boolean isUriSecured(String uri) {
        return endpointService.isUriSecured(uri);
    }

    public boolean isUriUnsecured(String uri) {
        return endpointService.isUriUnsecured(uri);
    }
}
