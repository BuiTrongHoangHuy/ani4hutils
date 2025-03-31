package site.ani4h.shared.errors;

import org.springframework.http.HttpStatus;

public class ErrorBadRequest extends BaseException {
    public ErrorBadRequest(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
