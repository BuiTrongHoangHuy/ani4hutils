package site.ani4h.auth.externalprovider;

import site.ani4h.shared.common.Uid;

import java.time.LocalDateTime;

public class ExternalAuthProvider {
    private Uid id;
    private String name;
    private String endPoint;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
