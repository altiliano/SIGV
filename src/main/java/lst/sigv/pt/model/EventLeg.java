package lst.sigv.pt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Afonseca on 06/02/21
 */
@Getter
@Setter
@Entity (name = "legs")
public class EventLeg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int legNumber;
    private AirportEntity depart;

    private AirportEntity destination;
    @ManyToMany( cascade = CascadeType.MERGE)
    @JoinTable(name = "legs_planes", joinColumns = {@JoinColumn(name = "legs_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "plane_id", referencedColumnName = "id")})
    private Set<PlaneEntity> planes;
}
