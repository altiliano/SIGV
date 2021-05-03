package lst.sigv.pt.model.api;

import lombok.Getter;
import lombok.Setter;
import lst.sigv.pt.validator.FieldsValueMatch;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@FieldsValueMatch(field = "newPassword", fieldMatch = "confirmPassword", message = "{password.fieldsValueMatch}")
public class RestResetPasswordForm implements Serializable {

    private String userName;

    @NotEmpty(message = "{newPassword.notEmpty}")
    private String newPassword;
    @NotEmpty(message = "{confirmPassword.notEmpty}")
    private String confirmPassword;
}
