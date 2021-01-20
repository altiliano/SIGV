package lst.sigv.pt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Afonseca on 18/01/21
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUserStatusException extends RuntimeException{
    public InvalidUserStatusException(String message){
        super(message);
    }
}
