package skrobman.dev.springstart.common.annotation;

import skrobman.dev.springstart.common.validation.StrongPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "Your password must contain at least one number," +
            " one lowercase letter," +
            " one uppercase letter," +
            " one special character (@#$%^&+=)," +
            " must not contain any spaces " +
            "and must have 12 characters minimum.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
