package lst.sigv.pt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Afonseca on 13/11/20
 */
@Getter
@Setter
@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String city;
    private int  points;
    private long hoursFlown;
    private int pireps;
    private int averageLandingRate;
    private String currentLocation;
    private Date birthDate;
    private String username;
    private String password;


    @OneToMany( cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "users_authority", joinColumns = {@JoinColumn ( name = "USER_ID", referencedColumnName = "ID")})
    private Set<AuthorityEntity> authorities = new HashSet<>();

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private BookingEntity booking;

    private UserStatus status;
    @Column(name = "profile_photo_id", nullable = true, unique = true)
    private Long profilePhotoId;
}
