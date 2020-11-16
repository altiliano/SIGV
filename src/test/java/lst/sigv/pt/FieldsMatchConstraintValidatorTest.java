package lst.sigv.pt;

import lst.sigv.pt.model.api.RestUserRegistration;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Afonseca on 16/11/20
 */
public class FieldsMatchConstraintValidatorTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidPasswords() {
        RestUserRegistration userRegistration = new RestUserRegistration();
        userRegistration.setFirstName("test");
        userRegistration.setLastName("test");
        userRegistration.setBirthDate(new Date(1111111L));
        userRegistration.setCity("Lisbon");
        userRegistration.setCountry("Portugal");
        userRegistration.setEmail("aa@gmail.com");
        userRegistration.setUsername("username");
        userRegistration.setIvaoId(111111);
        userRegistration.setVatsimId(111111);
        userRegistration.setPassword("password");
        userRegistration.setConfirmPassword("password");

        Set<ConstraintViolation<RestUserRegistration>> constraintViolations = validator.validate(userRegistration);

        assertEquals(constraintViolations.size(), 0);
    }

    @Test
    public void testInvalidPassword() {
        RestUserRegistration userRegistration = new RestUserRegistration();
        userRegistration.setFirstName("test");
        userRegistration.setLastName("test");
        userRegistration.setBirthDate(new Date(1111111L));
        userRegistration.setCity("Lisbon");
        userRegistration.setCountry("Portugal");
        userRegistration.setEmail("aa@gmail.com");
        userRegistration.setUsername("username");
        userRegistration.setIvaoId(111111);
        userRegistration.setVatsimId(111111);
        userRegistration.setPassword("password");
        userRegistration.setConfirmPassword("invalid");
        Set<ConstraintViolation<RestUserRegistration>> constraintViolations = validator.validate(userRegistration);

        assertEquals(constraintViolations.size(), 1);
    }
}
