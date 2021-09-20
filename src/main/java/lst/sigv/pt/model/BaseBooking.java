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
public class BaseBooking {

    @Column(name = "depart_icao_code")
    private String departIcaoCode;
    @Column(name = "depart_iata_code")
    private String departIataCode;
    @Column(name = "depart_city")
    private String departCity;
    @Column(name = "depart_country")
    private String departCountry;
    @Column(name = "depart_latitude")
    private String departLatitude;
    @Column(name = "depart_longitude")
    private String departLongitude;
    @Column(name = "depart_airport_name")
    private String departAirportName;

    @Column(name = "arrival_icao_code")
    private String arrivalIcaoCode;
    @Column(name = "arrival_iata_code")
    private String arrivalIataCode;
    @Column(name = "arrival_city")
    private String arrivalCity;
    @Column(name = "arrival_country")
    private String arrivalCountry;
    @Column(name = "arrival_latitude")
    private String arrivalLatitude;
    @Column(name = "arrival_longitude")
    private String arrivalLongitude;
    @Column(name = "arrival_airport_name")
    private String arrivalAirportName;

}

