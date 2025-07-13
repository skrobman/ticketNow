package skrobman.dev.springstart.common.validation;

import skrobman.dev.springstart.common.annotation.StrongPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    private static final String Password_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{12,}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        if(value == null) return false;
        return value.matches(Password_PATTERN);
    }
}
