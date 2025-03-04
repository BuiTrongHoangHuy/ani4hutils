package site.ani4h.api.middleware.jwt_spring_security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private String jwtSecret = "P7nK4vQ9mRwT2uJ5aY8eD3bHsZ6iL1xO";
    private int accessTokenExpirationMs = 1000 * 60 * 30;
    private int refreshTokenExpirationMs = 1000 * 60 * 60 * 24 * 7;

    public String generateJwtToken(Authentication authentication, long jwtExpirationInMs, String tokenType) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getEmail()))
                .setIssuedAt(java.util.Date.from(java.time.Instant.now()))
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationInMs))
                .claim("token_type", tokenType)
                .signWith(key(),SignatureAlgorithm.HS256)
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
    public String generateAccessToken(Authentication authentication) {
        return generateJwtToken(authentication, accessTokenExpirationMs, "access");
    }

    // generateRefreshToken
    public String generateRefreshToken(Authentication authentication) {
        return generateJwtToken(authentication, refreshTokenExpirationMs, "refresh");
    }

    // get email from token
    public String getEmailFromJwtToken(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
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
