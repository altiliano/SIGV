package lst.sigv.pt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Afonseca on 12/02/21
 */
@Entity( name = "airports")
@Getter
@Setter
public class AirportEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;

    private String icaoCode;

    private String iataCode;

    private String city;

    private String country;

    private String latitude;

    private String longitude;

    private String name;

}
