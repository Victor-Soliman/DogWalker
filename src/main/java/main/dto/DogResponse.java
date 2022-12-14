package main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import main.repository.entities.Walker;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DogResponse {

    private Integer id;

    private String name;

    private Integer age;

    private String genealogy;

    private Boolean hasMicrochip;

    private String chipNumber;
//
//    private User user;
//
//    private Walker walker;
//
//    private Order order;

}
