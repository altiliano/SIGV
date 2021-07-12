package lst.sigv.pt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Created by Afonseca on 06/02/21
 */
@Getter
@Setter
@Entity(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "status", nullable = false)
    private EventStatus status;
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;
    @Column(name = "status_date", nullable = false)
    private Date statusDate;
    @OneToMany
    @JoinTable(name = "events_legs", joinColumns = @JoinColumn(name = "events_id"), inverseJoinColumns = @JoinColumn(name = "legs_id"))
    private Set<RouteEntity> legs;
}
