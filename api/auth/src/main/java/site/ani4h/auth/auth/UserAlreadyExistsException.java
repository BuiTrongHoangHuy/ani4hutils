package site.ani4h.auth.auth;

import org.springframework.http.HttpStatus;
import site.ani4h.auth.middleware.logger.BaseException;

public class UserAlreadyExistsException extends BaseException {
    public UserAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
