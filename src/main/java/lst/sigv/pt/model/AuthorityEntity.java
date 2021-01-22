package lst.sigv.pt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Afonseca on 13/11/20
 */
@Setter
@Getter
@Entity
public class AuthorityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;
    @ManyToMany( mappedBy = "authorities")
    private Set<UserEntity> users;
}
