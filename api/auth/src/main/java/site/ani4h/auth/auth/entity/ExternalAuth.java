package site.ani4h.auth.auth.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class ExternalAuth {
    private Uid id;
    private Uid userId;
    private Uid providerId;
    private String token;
    private String externalUserId;
}
