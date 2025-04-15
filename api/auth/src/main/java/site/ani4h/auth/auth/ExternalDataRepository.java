package site.ani4h.auth.auth;

import org.springframework.stereotype.Component;
import site.ani4h.auth.auth.entity.ExternalLoginRequest;
import site.ani4h.auth.auth.entity.UserData;
import site.ani4h.auth.externalprovider.ExternalAuthProvider;
import site.ani4h.shared.errors.ErrorBadRequest;

@Component
public class ExternalDataRepository {
    private final GoogleExternalDataProvider googleProvider;
    public ExternalDataRepository(GoogleExternalDataProvider googleProvider) {
        this.googleProvider = googleProvider;
    }
    public  UserData getUserData(ExternalLoginRequest request, ExternalAuthProvider provider) {
        switch (provider.getName()) {
            case "google" : {
                return googleProvider.getUserData(request.getToken());
            }
            case "facebook" : {

            }
            default: {
                throw new ErrorBadRequest("");
            }
        }
    }
}
