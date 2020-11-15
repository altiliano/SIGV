package lst.sigv.pt.model.api;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Afonseca on 15/11/20
 */
@Getter
@Setter
public class RestUserRegistration implements Serializable {
    @NotEmpty
    private String firstName;
    @NotEmpty(message = "{lastName.NotEmpty}")
    private String lastName;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String country;
    @NotEmpty
    private String city;
    @NotNull
    private int ivaoId;
    @NotNull
    private int vatsimId;
    @NotNull
    private Date birthDate;
    @NotEmpty(message = "{username.NotEmpty}")
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;
}
