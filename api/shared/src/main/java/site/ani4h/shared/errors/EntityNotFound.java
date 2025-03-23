package site.ani4h.shared.errors;

import org.springframework.http.HttpStatus;

public class EntityNotFound extends BaseException {
    public EntityNotFound(String entity) {
        super(String.format("%s not found",entity), HttpStatus.NOT_FOUND);
    }
}
