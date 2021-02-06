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

    private String name;

    private EventStatus status;

    private Date creationDate;

    private Date statusDate;

    @OneToMany
    @JoinTable(name = "events_legs", joinColumns = @JoinColumn(name = "events_id"), inverseJoinColumns = @JoinColumn(name = "legs_id"))
    private Set<EventLeg> legs;
}
