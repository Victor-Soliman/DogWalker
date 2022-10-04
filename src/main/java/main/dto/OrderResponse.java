package main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.repository.entities.Dog;
import main.repository.entities.User;
import main.repository.entities.Walker;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.OneToOne;
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
