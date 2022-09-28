package main.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.repository.entities.Dog;
import main.repository.entities.User;
import main.repository.entities.Walker;
import org.springframework.stereotype.Component;

import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;


@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    private Integer id;

    @Schema(description = "Order date of the input Model", example = "2021-02-12")
    private Date orderDate;

    @Schema(description = "Return date of the input Model", example = "2021-02-12")
    private Date returnDate;

    @NotBlank
    @Schema(description = "days walked of the input Model", example = "3")
    private Integer daysWalked;

    @NotBlank
    @Schema(description = "Cost per day of the input Model", example = "50")
    private Double costPerDay;


    @Schema(description = "Checking if the input Model was walked", example = "true")
    private Boolean dogWalked;

    @Schema(description = "Walker input Model", example = "Marc Jhon")
    private Walker walker;

    @Schema(description = "User input Model", example = "Sam Anthony")
    private User user;

    @Schema(description = " Dog input Model", example = "Tikva")
    private Dog dog;
}
