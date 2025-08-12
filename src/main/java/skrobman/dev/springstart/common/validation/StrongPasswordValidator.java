package skrobman.dev.springstart.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import skrobman.dev.springstart.common.annotation.StrongPassword;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    private static final String Password_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$!%^&+=])(?=\\S+$).{12,}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        if(value == null) return false;
        return value.matches(Password_PATTERN);
    }
}
