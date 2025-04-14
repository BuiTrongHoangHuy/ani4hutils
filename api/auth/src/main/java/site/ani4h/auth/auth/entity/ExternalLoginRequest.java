package site.ani4h.auth.auth.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

@Data
@Getter
@Setter
public class ExternalLoginRequest {
    private String token;
    private Uid provider;
 }
