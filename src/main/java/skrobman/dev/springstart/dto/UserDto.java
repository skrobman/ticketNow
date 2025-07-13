package skrobman.dev.springstart.dto;

import skrobman.dev.springstart.common.annotation.StrongPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDto {
    @NotBlank(message = "Email cannot be empty")
    @NotNull(message = "Email cannot be null")
    @Email(message = "Incorrect email")
    private String email;

    @StrongPassword
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
