package skrobman.dev.springstart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skrobman.dev.springstart.common.annotation.StrongPassword;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Incorrect email")
    @JsonProperty("email")
    private String email;

    @StrongPassword
    private String password;
}
