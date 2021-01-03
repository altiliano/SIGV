package lst.sigv.pt.model.api;

import lombok.Getter;
import lombok.Setter;
import lst.sigv.pt.validator.FieldsValueMatch;

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
@FieldsValueMatch.List({@FieldsValueMatch(field = "password", fieldMatch = "confirmPassword", message = "{password.FieldsValueMatch}"),
        @FieldsValueMatch(field = "email", fieldMatch = "emailConfirmation", message = "{email.FieldsValueMatch}")})
public class RestUserRegistration implements Serializable {
    @NotEmpty(message = "{firstName.NotEmpty}")
    private String firstName;
    @NotEmpty(message = "{lastName.NotEmpty}")
    private String lastName;
    @NotEmpty(message = "{email.NotEmpty}")
    @Email
    private String email;
    @NotEmpty(message = "{emailConfirmation.NotEmpty}")
    private String emailConfirmation;
    @NotNull(message = "{birthDate.NotNul}")
    private Date birthDate;
    @NotEmpty(message = "{password.NotEmpty}")
    private String password;
    @NotEmpty(message = "{confirmPassword.NotEmpty}")
    private String confirmPassword;
}
