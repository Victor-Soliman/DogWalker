package main.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.repository.entities.Dog;
import main.repository.entities.Order;
import main.repository.entities.UserRole;
import main.repository.entities.Walker;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotBlank
    @Schema(description = "Name of the input Model", example = "Mark Enderson", required = true)
    private String name;

    @NotNull
    @Min(value = 15)
    @Schema(description = "Age of the input Model", example = "31", required = true)
    private Integer age;

    @Schema(description = "Password of the input Model", example = "31AKIJ!@3s!")
    private String password;

    @Schema(description = "City Name of the input Model", example = "London")
    private String city;

    @NotBlank
    @Schema(description = "Phone number of the input Model", example = "(865) 2408023", required = true)
    private String phoneNumber;

    @Schema(description = "Address of the input Model", example = "(865) 2408023")
    private String address;

    @NotBlank
    @Schema(description = "Email of the input Model", example = "dfaaasd@gmail.com", required = true)
    private String email;

    @Schema(description = "Checking if the input Model has dog", example = "(865) 2408023")
    @NotNull
    private Boolean hasDog;

    @Schema(description = "Checking if the input Model is blocked", example = "(865) 2408023")
    private boolean isUserBlocked;

    @Schema(description = "Checking the input Model user role", example = "(865) 2408023")
    private UserRole userRole;

    @Schema(description = "Checking the input Model dogs", example = "dog")
    private List<Dog> dogs;

    @Schema(description = "Checking the input Model walker", example = "walker")
    private Walker walker;

    @Schema(description = "Checking the input Model order", example = "order")
    private Order order;

}
