package lst.sigv.pt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by Afonseca on 18/09/21
 */
@MappedSuperclass
@Getter
@Setter
public class BaseAirport {

    @Column(name = "icao_code")
    private String icaoCode;
    @Column(name = "iata_code")
    private String iataCode;

    private String city;

    private String country;

    private String latitude;

    private String longitude;

    private String name;
}
