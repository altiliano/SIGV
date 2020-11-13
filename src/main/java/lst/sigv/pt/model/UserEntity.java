package lst.sigv.pt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Created by Afonseca on 13/11/20
 */
@Getter
@Setter
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String city;
    private int ivaoId;
    private int vatsimId;
    private Date birthDate;
    private String username;
    private String password;
    @ManyToMany( cascade = CascadeType.MERGE)
    @JoinTable(name = "user_authority", joinColumns = {@JoinColumn ( name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
    private Set<Authority> authorities;
}
