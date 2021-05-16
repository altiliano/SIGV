package lst.sigv.pt.model.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

import java.io.Serializable;

/**
 * Created by Afonseca on 12/02/21
 */
@Getter
@Setter
@Builder
public class RestAirport implements Serializable {

    public RestAirport() {
    }

    public RestAirport(String id, String icaoCode, String iataCode, String city, String country, String latitude, String longitude, String name) {
        this.id = id;
        this.icaoCode = icaoCode;
        this.iataCode = iataCode;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    private String id;

    private String icaoCode;

    private String iataCode;

    private String city;

    private String country;

    private String latitude;

    private String longitude;

    private String name;
}
