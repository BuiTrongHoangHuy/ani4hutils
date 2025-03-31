package site.ani4h.shared.middlewares;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import site.ani4h.shared.common.Requester;
import site.ani4h.shared.common.Uid;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
public class RequestExtractor implements HandlerInterceptor {
    public RequestExtractor() {
    }
    private static PrivateKey privateKey = null;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authHeader = request.getHeader("Authorization");
        System.out.println("Bearer " + authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return true;
        }

        String token = authHeader.substring(7);
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Requester requester = new Requester();
            requester.setId(new Uid(claims.getSubject()).getLocalId());
            requester.setIp(request.getRemoteAddr());
            requester.setRole(claims.get("scope",String.class));
            requester.setUserAgent(request.getHeader("User-Agent"));
            request.setAttribute("requester", requester);
            System.out.println("Requester " + requester.getIp());
        }catch (Exception e) {
            return true;
        }

        return true;
    }

    private static PrivateKey key() {
        if (privateKey == null) {
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                String privateKeyPEM = getPrivateKeyPEM();
                byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
                privateKey = keyFactory.generatePrivate(keySpec);
            } catch (IOException e) {
                throw new RuntimeException("Error reading private key file", e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("RSA algorithm not found", e);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException("Invalid key format", e);
            }
        }
        return privateKey;
    }
    private static String getPrivateKeyPEM() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String privateKeyPEM;
        try (InputStream is = classLoader.getResourceAsStream("private.key")) {
            if (is == null) {
                throw new IllegalArgumentException("File private.key not found in resources");
            }

            privateKeyPEM = new String(is.readAllBytes());
        }

        privateKeyPEM = privateKeyPEM
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
        return privateKeyPEM;
    }
}