package skrobman.dev.springstart.dto;

import skrobman.dev.springstart.common.annotation.StrongPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {
    @NotBlank(message = "Email cannot be empty")
    @NotNull(message = "Email cannot be null")
    @Email(message = "Incorrect email")
    private String email;

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank!")
    @Size(min = 2, message = "Username must containt 2 or more characters")
    private String username;

    @StrongPassword
    private String password;

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
