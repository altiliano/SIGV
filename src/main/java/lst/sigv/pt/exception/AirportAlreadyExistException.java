package lst.sigv.pt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Afonseca on 12/02/21
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AirportAlreadyExistException extends RuntimeException{

    public AirportAlreadyExistException(String message) {
        super(message);
    }
}
