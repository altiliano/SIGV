package lst.sigv.pt.model.api;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lst.sigv.pt.validator.FieldsValueMatch;

@Getter
@Setter
@FieldsValueMatch(field = "newPassword", fieldMatch = "confirmPassword", message = "{password.fieldsValueMatch}")
public class RestChangePasswordForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;
    @NotEmpty(message = "{newPassword.notEmpty}")
    private String newPassword;
    @NotEmpty(message = "{confirmPassword.notEmpty}")
    private String confirmPassword;


}
