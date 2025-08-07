package skrobman.dev.springstart.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailDto {
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Incorrect email")
    private String email;
}
