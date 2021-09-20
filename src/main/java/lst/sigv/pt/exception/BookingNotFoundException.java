package lst.sigv.pt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Afonseca on 18/09/21
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookingNotFoundException  extends  RuntimeException{
    public BookingNotFoundException(String message) {
        super(message);
    }
}
