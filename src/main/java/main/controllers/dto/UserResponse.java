package main.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.repository.entities.Dog;
import main.repository.entities.Order;
import main.repository.entities.UserRole;
import main.repository.entities.Walker;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserResponse {

    private Integer id;

    private String name;

    private Integer age;

    private String password;

    private String city;

    private String phoneNumber;

    private String address;

    private String email;

    private Boolean hasDog;

    private Boolean userBlocked;

    private UserRole userRole;


}
