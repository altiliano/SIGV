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
public class AirportEntity extends BaseAirport {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;


    @OneToMany
    private Set<RouteEntity> routes = new HashSet<>();

}
