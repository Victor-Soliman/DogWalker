package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.entities.UserRole;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private int id ;

    @NotBlank
    @Schema(description = "Name of the input Model", example = "Mark Enderson", required = true)
    private String name;

    @NotNull
    @Min(value = 15)
    @Schema(description = "Age of the input Model", example = "31", required = true)
    private Integer age;

    @NotBlank
    @Schema(description = "Password of the input Model", example = "31AKIJ!@3s!")
    private String password;

    @Schema(description = "City Name of the input Model", example = "London")
    private String city;

    @Schema(description = "Phone number of the input Model", example = "(865) 2408023", required = true)
    private String phoneNumber;

    @NotBlank
    @Schema(description = "Address of the input Model", example = "(865) 2408023")
    private String address;

    @Schema(description = "Email of the input Model", example = "dfaaasd@gmail.com", required = true)
    private String email;

    @Schema(description = "Checking if the input Model has dog", example = "(865) 2408023")
    private Boolean hasDog;

    @Schema(description = "Checking if the input Model is blocked", example = "(865) 2408023")
    private Boolean userBlocked;

    @Schema(description = "Checking the input Model user role", example = "(865) 2408023")
    private UserRole userRole;


}
