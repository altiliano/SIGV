package lst.sigv.pt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Afonseca on 16/11/20
 */
@Getter
@Setter
@Entity(name = "planes")
public class PlaneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String registration;
    private String photoUrl;
    private String textureUrl;
    private PlaneStatus status;
    private String aircraftType;
    @OneToMany(mappedBy = "plane")
    private Set<BookingEntity> bookings = new HashSet<>();
    @ManyToMany(mappedBy = "planes")
    private Set<RouteEntity> routes = new HashSet<>();
}
