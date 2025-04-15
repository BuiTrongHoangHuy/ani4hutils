package site.ani4h.auth.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import site.ani4h.auth.auth.entity.GoogleUserData;
import site.ani4h.auth.auth.entity.UserData;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GoogleExternalDataProvider {

    public static UserData getUserData(String token) {
        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.googleapis.com/oauth2/v2/userinfo"))
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                 var data = mapper.readValue(response.body(), GoogleUserData.class);
                 return new UserData(null,data.getFamilyName(),data.getGivenName());
            } else {
                System.err.println("Failed to fetch user data. Status: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
