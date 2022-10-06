package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DogRequest {

    private Integer id ;


    @NotBlank
    @Schema(description = "Name of the input Model", example = "Mark Anthony", required = true)
    private String name;

    @NotNull
    @Schema(description = "Age of the input Model", example = "Tikva", required = true)
    private Integer age;

    @Schema(description = "Dog genealogy (race) of the input Model", example = "Labrador")
    private String genealogy;

    @Schema(description = "Checking if the input Model has microchip available", example = "true", required = true)
    private Boolean hasMicrochip;

    @Schema(description = "ChipNumber of the input Model", example = "49-851-4989")
    private String chipNumber;


}
