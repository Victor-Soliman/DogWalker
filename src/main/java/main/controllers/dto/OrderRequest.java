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
import java.sql.Date;


@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    @Schema(description = "Order date of the input Model", example = "2021-02-12")
    private Date orderDate;

    @Schema(description = "Return date of the input Model", example = "2021-02-12")
    private Date returnDate;

    @Schema(description = "Walk cost of the input Model", example = "10.45")
    private Double walk_cost;

    @Schema(description = "Checking if the input Model was walked", example = "true")
    private Boolean dogWalked;

//
//    @Schema(description = "Walker input Model", example = "Marc Jhon")
//    private Walker walker;
//
//    @Schema(description = "User input Model", example = "Sam Anthony")
//    private User user;
//
//    @Schema(description = " Dog input Model", example = "Tikva")
//    private Dog dog;
}
