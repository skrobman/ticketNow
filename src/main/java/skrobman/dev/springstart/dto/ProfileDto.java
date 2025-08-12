package skrobman.dev.springstart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    @NotBlank(message = "Full name cannot be blank")
    @Size(max = 100, message = "Full name must be less than 100 characters")
    @JsonProperty(value = "full_name")
    private String fullName;

    @JsonProperty(value = "phone_number")
    @Pattern(regexp = "\\+?[0-9\\-]{7,15}", message = "Phone number format is invalid")
    private String phoneNumber;

    @URL(message = "Avatar URL must be a valid URL")
    @JsonProperty(value = "avatar_url")
    private String avatarUrl;
}
