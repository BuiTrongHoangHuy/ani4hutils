package site.ani4h.auth.auth.error;

import org.springframework.http.HttpStatus;
import site.ani4h.shared.errors.BaseException;

public class ErrorInvalidCredentials extends BaseException {
  public ErrorInvalidCredentials() {
    super("Invalid credentials", HttpStatus.BAD_REQUEST);
  }
}
