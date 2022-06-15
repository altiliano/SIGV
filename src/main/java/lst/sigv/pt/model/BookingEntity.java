package lst.sigv.pt.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Afonseca on 16/11/20
 */
@Getter
@Setter
@Entity(name = "bookings")
public class BookingEntity extends BaseBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne( mappedBy = "booking")
    private UserEntity user;
    private BookingStatus status;
    @ManyToOne
    @JoinColumn(name = "plane_id", nullable = false, updatable = false)
    private PlaneEntity plane;
    @Column(name = "create_date")
    private Date createdDate;
    @Column(name = "status_date")
    private Date statusDate;

}
