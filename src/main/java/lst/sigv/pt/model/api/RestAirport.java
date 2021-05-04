package lst.sigv.pt.model.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Afonseca on 12/02/21
 */
@Getter
@Setter
public class RestAirport implements Serializable {

    private String id;

    private String icaoCode;

    private String iataCode;

    private String city;

    private String country;

    private String latitude;

    private String longitude;

    private String name;
}
