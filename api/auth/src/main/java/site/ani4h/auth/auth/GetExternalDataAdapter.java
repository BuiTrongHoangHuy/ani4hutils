package site.ani4h.auth.auth;

import site.ani4h.auth.auth.entity.ExternalLoginRequest;
import site.ani4h.auth.auth.entity.UserData;
import site.ani4h.auth.externalprovider.ExternalAuthProvider;

public class GetExternalDataAdapter {
    public static  UserData getUserData(ExternalLoginRequest request, ExternalAuthProvider provider) {
        switch (provider.getName()) {
            case "google" : {
                return GoogleExternalDataProvider.getUserData(request.getToken());
            }
            case "facebook" : {

            }
            default: {
                return null;
            }
        }
    }
}
