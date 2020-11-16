package lst.sigv.pt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Afonseca on 16/11/20
 */
@Getter
@Setter
@Entity(name = "bookings")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne( mappedBy = "booking")
    private UserEntity user;
    private BookingStatus status;
    @ManyToOne
    @JoinColumn(name = "plane_id")
    private PlaneEntity plane;
    private Date createdDate;
    private Date statusDate;
}
