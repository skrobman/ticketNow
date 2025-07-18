package skrobman.dev.springstart.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import skrobman.dev.springstart.common.validation.StrongPasswordValidator;

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
