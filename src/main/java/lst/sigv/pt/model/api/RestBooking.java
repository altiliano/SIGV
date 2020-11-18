package lst.sigv.pt.model.api;

import lombok.Data;
import lst.sigv.pt.model.BookingStatus;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Afonseca on 18/11/20
 */
@Data
public class RestBooking implements Serializable {
    private long id;
    private RestUser user;
    private BookingStatus status;
    private Date createdDate;
    private Date statusDate;
}
