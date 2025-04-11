package site.ani4h.shared.errors;

import org.springframework.http.HttpStatus;

public class ErrorUnauthorized extends BaseException{
    public ErrorUnauthorized() {
        super("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
}
