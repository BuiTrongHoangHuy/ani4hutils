package site.ani4h.auth.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.ani4h.auth.auth.entity.UserData;
import site.ani4h.shared.common.Image;

import java.util.Collections;

@Component
public class GoogleExternalDataProvider {
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    public GoogleExternalDataProvider() {
    }
    public  UserData getUserData(String token) {
        try {
            var verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singleton(this.clientId))
                    .build();
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String userId = payload.getSubject();
                String email = payload.getEmail();
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");

                String familyName = (String) payload.get("family_name");

                String givenName = (String) payload.get("given_name");
                var avatar =  new Image();
                avatar.setUrl(pictureUrl);
                return new UserData(userId,email,null,familyName,givenName,avatar);
            } else {
                throw new RuntimeException("Invalid token");
            }
        } catch (
                Exception e
        ) {
            throw new RuntimeException(e);
        }
    }
}
