package lst.sigv.pt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    private String depart;
    private String destination;
    @ManyToMany( cascade = CascadeType.MERGE)
    @JoinTable(name = "route_planes", joinColumns = {@JoinColumn(name = "route_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "plane_id", referencedColumnName = "id")})
    private Set<PlaneEntity> planes;
}
