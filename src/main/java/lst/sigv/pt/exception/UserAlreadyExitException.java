package lst.sigv.pt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Afonseca on 26/01/21
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExitException extends RuntimeException {
    public UserAlreadyExitException(String message) {
        super(message);
    }
}
