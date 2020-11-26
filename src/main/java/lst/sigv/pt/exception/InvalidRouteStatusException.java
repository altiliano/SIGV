package lst.sigv.pt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Afonseca on 23/11/20
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRouteStatusException extends RuntimeException {

    public InvalidRouteStatusException() {
    }

    public InvalidRouteStatusException(String message) {
        super(message);
    }

    public InvalidRouteStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRouteStatusException(Throwable cause) {
        super(cause);
    }

    public InvalidRouteStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
