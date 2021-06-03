package lst.sigv.pt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany
    private Set<RouteEntity> routes = new HashSet<>();

}
