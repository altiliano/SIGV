package lst.sigv.pt.model;

import lombok.Getter;
import lombok.Setter;
import org.mapstruct.EnumMapping;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Afonseca on 16/11/20
 */
@Getter
@Setter
@Entity(name = "routes")
public class RouteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn( name = "airport_id", referencedColumnName = "id", nullable = false, unique = true, updatable = false)
    private AirportEntity depart;
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "route")
    private Set<AirportEntity> destinations = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private RouteStatus status;
    @OneToMany( cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "routes")
    private Set<PlaneEntity> planes;
}
