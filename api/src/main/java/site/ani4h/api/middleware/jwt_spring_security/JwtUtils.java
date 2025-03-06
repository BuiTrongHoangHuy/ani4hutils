package site.ani4h.api.middleware.jwt_spring_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access-token-expiration-ms}")
    private int accessTokenExpirationMs;

    @Value("${jwt.refresh-token-expiration-ms}")
    private int refreshTokenExpirationMs;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public String generateJwtToken(String email, long jwtExpirationInMs, String tokenType) {
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(email);

        return Jwts.builder()
                .setSubject(String.valueOf(userDetails.getUserId())) // Dùng email làm subject thay vì lấy từ UserDetailsImpl
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .claim("token_type", tokenType)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

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
            return claims.get("token_type", String.class); // Lấy claim token_type
        } catch (Exception e) {
            logger.error("Error extracting token_type: {}", e.getMessage());
            return null;
        }
    }

    // generateAccessToken
    public String generateAccessToken(String email) {
        return generateJwtToken(email, accessTokenExpirationMs, "access");
    }

    // generateRefreshToken
    public String generateRefreshToken(String email) {
        return generateJwtToken(email, refreshTokenExpirationMs, "refresh");
    }

    // get email from token
    public String getEmailFromJwtToken(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
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
