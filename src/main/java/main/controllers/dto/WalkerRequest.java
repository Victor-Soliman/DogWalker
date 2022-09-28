package main.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.repository.entities.User;
import org.springframework.stereotype.Component;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WalkerRequest {

    private Integer id;


    @NotBlank
    @Schema(description = "Name of the input Model", example = "John Martin", required = true)
    private String name;

    @NotNull
    @Min(value = 18)
    @Schema(description = "Age of the input Model", example = "25", required = true)
    private Integer age;

    @Schema(description = "Password of the input Model", example = "4AdwERRs!989")
    private String password;

    @Schema(description = "City name of the input Model", example = "London")
    private String city;

    @NotBlank
    @Schema(description = "Phone number of the input Model", example = "49-851-4989", required = true)
    private String phoneNumber;

    @Schema(description = "Address of the input Model", example = "Piata Unirii 57", required = true)
    private String address;

    @NotBlank
    @Schema(description = "Email address of the input Model", example = "Jhon@gmail.com", required = true)
    private String email;

    @Schema(description = "Years of experience of the input Model", example = "3")
    private Integer yearsOfExperience;

    @Schema(description = "Checking if the input Model is available", example = "true")
    private Boolean isAvailable;

}
