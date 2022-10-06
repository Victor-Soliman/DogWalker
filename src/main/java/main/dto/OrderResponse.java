package main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.entities.Dog;
import main.entities.User;
import main.entities.Walker;
import org.springframework.stereotype.Component;

import java.sql.Date;


@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderResponse {

    private Integer id;

    private Date orderDate;

    private Date returnDate;

    private Integer daysWalked;

    private Double costPerDay;

    private Double walkCost;

    private Boolean dogWalked;

    private Walker walker;

    private User user;

    private Dog dog;

}
