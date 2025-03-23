package site.ani4h.shared.errors;

import org.springframework.http.HttpStatus;

@lombok.Getter
@lombok.Setter
public class BaseException extends RuntimeException {
    private final HttpStatus status;
    private  String debugField = "";
    public BaseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }


}
