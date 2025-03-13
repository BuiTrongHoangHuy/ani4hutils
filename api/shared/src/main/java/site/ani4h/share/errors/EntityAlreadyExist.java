package site.ani4h.share.errors;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExist  extends BaseException {
    public EntityAlreadyExist(String message) {
        super(String.format("%s already exist",message), HttpStatus.CONFLICT);
    }
}
