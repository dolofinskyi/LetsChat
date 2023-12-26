package ua.dolofinskyi.letschat.security.jwt;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.dolofinskyi.letschat.features.user.User;
import ua.dolofinskyi.letschat.features.user.UserService;

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
    private final UserService userService;
    private String SIGNATURE_ALGORITHM;

    @PostConstruct
    private void init() {
        SIGNATURE_ALGORITHM = SignatureAlgorithm.forName(algorithm).getJcaName();
    }

    public SecretKey generateSecretKey(String secret) {
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(bytes, SIGNATURE_ALGORITHM);
    }

    public String generateToken(String subject, String secret) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .id(generateSecret())
                .subject(subject)
                .issuedAt(now)
                .notBefore(now)
                .expiration(exp)
                .signWith(generateSecretKey(secret))
                .compact();
    }

    public String generateSecret() {
        try {
            SecretKey key = KeyGenerator.getInstance(SIGNATURE_ALGORITHM).generateKey();
            return new String(key.getEncoded(), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException ignored) {

        }
        return null;
    }

    public boolean verifyToken(String subject, String token) {
        try {
            User user = (User) userService.loadUserByUsername(subject);
            String secret = user.getSecret();
            Jwts.parser()
                    .verifyWith(generateSecretKey(secret))
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
