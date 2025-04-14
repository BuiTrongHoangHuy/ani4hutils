package site.ani4h.auth.auth.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class GoogleUserData {
    private String id;
    private String email;

    @JsonProperty("verified_email")
    private boolean verifiedEmail;

    private String name;

    @JsonProperty("given_name")
    private String givenName;

    @JsonProperty("family_name")
    private String familyName;

    private String picture;
}
