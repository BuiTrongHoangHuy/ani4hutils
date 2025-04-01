package site.ani4h.auth.middleware.jwt_spring_security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.ani4h.auth.auth.entity.Auth;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.access-token-expiration-ms}")
    private int accessTokenExpirationMs;
    @Value("${jwt.refresh-token-expiration-ms}")
    private int refreshTokenExpirationMs;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.aud}")
    private String aud;
    @Value("${jwt.key-id}")
    private String keyId;
    private static PrivateKey privateKey = null;

    public boolean isAccessToken(String token) {
        return "access".equals(getTokenType(token));
    }

    public boolean isRefreshToken(String token) {
        return "refresh".equals(getTokenType(token));
    }

    private String getTokenType(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("token_type", String.class);
        } catch (Exception e) {
            logger.error("Error extracting token_type: {}", e.getMessage());
            return null;
        }
    }
    public String getClaim(String token,String key){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get(key, String.class);
        } catch (Exception e) {
            logger.error("Error extracting email: {}", e.getMessage());
            return null;
        }
    }
    // generateAccessToken
    public String generateAccessToken(Auth auth) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("kid",keyId)
                .setSubject(String.valueOf(auth.getUserId()))
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .setIssuer(issuer)
                .setAudience(aud)
                .claim("email", auth.getEmail())
                .claim("scope",auth.getRole().toString().toLowerCase())
                .signWith(key(), SignatureAlgorithm.RS256)
                .compact();
    }

    // generateRefreshToken
    public String generateRefreshToken(Auth auth) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("kid",keyId)
                .setSubject(String.valueOf(auth.getUserId()))
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .setIssuer(issuer)
                .setAudience(aud)
                .claim("email", auth.getEmail())
                .signWith(key(), SignatureAlgorithm.RS256)
                .compact();
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
    public static String SOURCE_R00T_PATH  = System.getProperty("user.dir");
    private static String getPrivateKeyPEM() throws IOException {
        String filePath = System.getenv().getOrDefault("PRIVATE_KEY_PATH", SOURCE_R00T_PATH+ "/config/private.key");
        if (!Files.exists(Paths.get(filePath))) {
            throw new IllegalArgumentException("File private.key not found at " + filePath);
        }

        String privateKeyPEM = Files.readString(Paths.get(filePath));

        privateKeyPEM = privateKeyPEM
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
        return privateKeyPEM;
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
