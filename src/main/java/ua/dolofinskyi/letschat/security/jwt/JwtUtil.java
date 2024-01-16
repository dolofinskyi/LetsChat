package ua.dolofinskyi.letschat.security.jwt;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;
import ua.dolofinskyi.letschat.security.cookie.CookieService;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    @Getter
    @Value("${jwt.expiration}")
    private long expiration;
    @Getter
    @Value("${jwt.algorithm}")
    private String algorithm;
    private String SIGNATURE_JCA_NAME;

    private final UserService userService;
    private final CookieService cookieService;

    @PostConstruct
    private void init() {
        SIGNATURE_JCA_NAME = SignatureAlgorithm.forName(algorithm).getJcaName();
    }

    public SecretKey generateSecretKey(String secret) {
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(bytes, SIGNATURE_JCA_NAME);
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .id(generateSecret())
                .subject(user.getUsername())
                .issuedAt(now)
                .notBefore(now)
                .expiration(exp)
                .signWith(generateSecretKey(user.getSecret()))
                .compact();
    }

    public String generateSecret() {
        try {
            SecretKey key = KeyGenerator.getInstance(SIGNATURE_JCA_NAME).generateKey();
            return new String(key.getEncoded(), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException ignored) {

        }
        return null;
    }

    public void verifyToken(String subject, String token)
            throws UsernameNotFoundException, JwtException {
        String secret = userService.findByUsername(subject).getSecret();
        Jwts.parser()
                .verifyWith(generateSecretKey(secret))
                .build()
                .parseSignedClaims(token);
    }

    public void setJwtCookies(HttpServletResponse response, String subject, String token) {
        cookieService.setCookie(response, "Subject", subject);
        cookieService.setCookie(response, "Token", token);
    }
}
