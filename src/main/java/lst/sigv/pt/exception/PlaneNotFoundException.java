package lst.sigv.pt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Afonseca on 19/11/20
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PlaneNotFoundException extends  RuntimeException{
    public PlaneNotFoundException(String message) {
        super(message);
    }
}
