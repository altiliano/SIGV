package lst.sigv.pt.model.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Afonseca on 15/11/20
 */
@Getter
@Setter
public class RestUser implements Serializable {
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
    private Set<RestAuthority> authorities;
    private byte[] profileImage;
}
