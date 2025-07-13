package skrobman.dev.springstart.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skrobman.dev.springstart.common.annotation.StrongPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
