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
@FieldsValueMatch.List({@FieldsValueMatch(field = "password", fieldMatch = "confirmPassword", message = "{password.fieldsValueMatch}"),
        @FieldsValueMatch(field = "email", fieldMatch = "emailConfirmation", message = "{email.fieldsValueMatch}")})
public class RestUserRegistration implements Serializable {
    @NotEmpty(message = "{firstName.notEmpty}")
    private String firstName;
    @NotEmpty(message = "{lastName.notEmpty}")
    private String lastName;
    @NotEmpty(message = "{email.notEmpty}")
    @Email
    private String email;
    @NotEmpty(message = "{emailConfirmation.notEmpty}")
    private String emailConfirmation;
    @NotNull(message = "{birthDate.notNul}")
    private Date birthDate;
    @NotEmpty(message = "{password.notEmpty}")
    private String password;
    @NotEmpty(message = "{confirmPassword.notEmpty}")
    private String confirmPassword;
}
